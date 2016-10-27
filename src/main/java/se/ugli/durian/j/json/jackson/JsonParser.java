package se.ugli.durian.j.json.jackson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;

public final class JsonParser {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NodeFactory nodeFactory;
    private final String rootElementName;
    private final String uri;
    private final boolean createArrayElements;
    private final String rootArrayChildElementName;

    private JsonParser(final String rootElementName, final String uri, final NodeFactory nodeFactory,
            final boolean createArrayElements, final String rootArrayChildElementName) {
        this.rootElementName = rootElementName;
        this.uri = uri;
        this.nodeFactory = nodeFactory;
        this.createArrayElements = createArrayElements;
        this.rootArrayChildElementName = rootArrayChildElementName;
    }

    static JsonParser apply(final JsonParserBuilder builder) {
        return new JsonParser(builder.getRootElementName(), builder.getUri(), builder.getNodeFactory(),
                builder.doCreateArrayElements(), builder.getRootArrayChildElementName());
    }

    public static JsonParser apply() {
        return apply(JsonParserBuilder.apply());
    }

    public Element parse(final byte[] bytes) {
        try {
            return parse(objectMapper.readTree(bytes));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Element parse(final Reader reader) {
        try {
            return parse(objectMapper.readTree(reader));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Element parse(final File file) {
        try {
            return parse(objectMapper.readTree(file));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Element parse(final URL url) {
        try {
            return parse(objectMapper.readTree(url));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Element parse(final String str) {
        try {
            return parse(objectMapper.readTree(str));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Element parse(final InputStream inputStream) {
        try {
            return parse(objectMapper.readTree(inputStream));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Element parse(final JsonNode rootNode) {
        if (rootNode instanceof ObjectNode)
            return createElementFromObjectNode(rootElementName, (ObjectNode) rootNode, null);
        else if (rootNode instanceof ArrayNode) {
            final MutableElement root = nodeFactory
                    .createElement(rootElementName, uri, null, new ArrayList<PrefixMapping>(0))
                    .as(MutableElement.class);
            appendArrayNode((ArrayNode) rootNode, rootArrayChildElementName, root);
            return root;
        } else if (rootNode instanceof ValueNode) {
            final MutableElement root = nodeFactory
                    .createElement(rootElementName, uri, null, new ArrayList<PrefixMapping>(0))
                    .as(MutableElement.class);
            if (!(rootNode instanceof NullNode))
                root.add(nodeFactory.createText(root, rootNode.asText()));
            return root;
        } else
            throw new IllegalStateException("Node type: " + rootNode.getClass());
    }

    private Element createElementFromObjectNode(final String elementName, final ObjectNode node, final Element parent) {
        final MutableElement element = nodeFactory
                .createElement(elementName, uri, parent, new ArrayList<PrefixMapping>(0)).as(MutableElement.class);
        for (final Iterator<String> i = node.fieldNames(); i.hasNext();) {
            final String fieldName = i.next();
            final JsonNode childNode = node.get(fieldName);
            if (childNode instanceof ValueNode)
                appendValueNode((ValueNode) childNode, fieldName, element);
            else if (childNode instanceof ObjectNode)
                appendObjectNode((ObjectNode) childNode, fieldName, element);
            else if (childNode instanceof ArrayNode)
                appendArrayNode((ArrayNode) childNode, fieldName, element);
            else
                throw new IllegalStateException("type: " + childNode.getClass().getName());
        }
        return element;
    }

    private void appendObjectNode(final ObjectNode objectNode, final String fieldName, final MutableElement parent) {
        parent.add(createElementFromObjectNode(fieldName, objectNode, parent));
    }

    private void appendValueNode(final ValueNode valueNode, final String fieldName, final MutableElement parent) {
        final String atttibuteValue = valueNode instanceof NullNode ? null : valueNode.asText();
        parent.add(nodeFactory.createAttribute(fieldName, uri, parent, atttibuteValue));
    }

    private void appendArrayNode(final ArrayNode arrayNode, final String elementName, final MutableElement _parent) {
        final MutableElement parent = getArrayParent(elementName, _parent);
        for (final JsonNode childNode : arrayNode)
            if (childNode instanceof ValueNode)
                parent.add(nodeFactory.createText(parent, childNode.asText()));
            else if (childNode instanceof ObjectNode)
                appendObjectNode((ObjectNode) childNode, elementName, parent);
            else if (childNode instanceof ArrayNode) {
                if (!createArrayElements)
                    throw new IllegalStateException(
                            "Can't parse json with nested arrays with 'createArrayElements' set to false");
                appendArrayNode((ArrayNode) childNode, elementName, parent);
            } else
                throw new IllegalStateException("type: " + childNode.getClass().getName());
    }

    private MutableElement getArrayParent(final String elementName, final MutableElement parent) {
        if (createArrayElements) {
            final JsonArrayElement arrayElement = new JsonArrayElement(elementName, uri, nodeFactory);
            parent.add(arrayElement);
            return arrayElement;
        }
        return parent;
    }
}
