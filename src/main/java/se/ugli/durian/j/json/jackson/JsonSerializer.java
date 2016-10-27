package se.ugli.durian.j.json.jackson;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.json.jackson.fieldvaluefactories.ByValueFieldValueFactory;

public final class JsonSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FieldValueFactory fieldValueFactory;

    private JsonSerializer(final FieldValueFactory fieldValueFactory) {
        this.fieldValueFactory = fieldValueFactory;
    }

    public static JsonSerializer apply() {
        return new JsonSerializer(new ByValueFieldValueFactory());
    }

    public static JsonSerializer apply(final FieldValueFactory fieldValueFactory) {
        return new JsonSerializer(fieldValueFactory);
    }

    public String serialize(final Element element) {
        try {
            final JsonNode jsonNode = createJsonNode(element);
            final StringWriter stringWriter = new StringWriter();
            objectMapper.writeValue(stringWriter, jsonNode);
            return stringWriter.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode createJsonNode(final Element element) {
        if (element instanceof JsonArrayElement)
            return createArrayFromArrayElement(element);
        else {
            final ObjectNode objectNode = objectMapper.createObjectNode();
            element.attributes().forEach(attribute -> {
                putAttribute(objectNode, attribute);
            });
            final Map<String, List<Element>> childElementNameMap = createElementNameMap(element);
            for (final String fieldName : childElementNameMap.keySet()) {
                final List<Element> childElementByName = childElementNameMap.get(fieldName);
                if (childElementByName.isEmpty())
                    throw new IllegalStateException();
                else if (childElementByName.size() == 1)
                    objectNode.set(fieldName, createJsonNode(childElementByName.get(0)));
                else {
                    final ArrayNode arrayNode = objectNode.arrayNode();
                    for (final Element arrayElement : childElementByName)
                        arrayNode.add(createJsonNode(arrayElement));
                    objectNode.set(fieldName, arrayNode);
                }
            }
            return objectNode;
        }
    }

    private Map<String, List<Element>> createElementNameMap(final Element element) {
        final Map<String, List<Element>> map = new LinkedHashMap<String, List<Element>>();
        element.elements().forEach(childElement -> {
            final String fieldName;
            if (childElement instanceof JsonArrayElement)
                fieldName = ((JsonArrayElement) childElement).arrayName;
            else
                fieldName = childElement.name();
            List<Element> list;
            if (map.containsKey(fieldName))
                list = map.get(fieldName);
            else {
                list = new ArrayList<Element>();
                map.put(fieldName, list);
            }
            list.add(childElement);
        });
        return map;
    }

    private void putAttribute(final ObjectNode objectNode, final Attribute attribute) {
        final String fieldName = attribute.name();
        final Object value = fieldValueFactory.create(attribute);
        if (value == null)
            objectNode.putNull(fieldName);
        else if (value instanceof Boolean)
            objectNode.put(fieldName, (Boolean) value);
        else if (value instanceof Long)
            objectNode.put(fieldName, (Long) value);
        else if (value instanceof Double)
            objectNode.put(fieldName, (Double) value);
        else
            objectNode.put(fieldName, value.toString());
    }

    private JsonNode createArrayFromArrayElement(final Element element) {
        final ArrayNode arrayNode = objectMapper.createArrayNode();
        element.elements().forEach(childElement -> {
            arrayNode.add(createJsonNode(childElement));
        });
        element.texts().forEach(text -> {
            final Object textValue = fieldValueFactory.create(text);
            if (textValue == null)
                arrayNode.addNull();
            else if (textValue instanceof Boolean)
                arrayNode.add((Boolean) textValue);
            else if (textValue instanceof Long)
                arrayNode.add((Long) textValue);
            else if (textValue instanceof Double)
                arrayNode.add((Double) textValue);
            else
                arrayNode.add(textValue.toString());
        });
        return arrayNode;
    }

}
