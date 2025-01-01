package io.durian.sax;

import io.durian.Attribute;
import io.durian.Content;
import io.durian.Element;
import io.durian.immutable.ImmutableAttribute;
import io.durian.immutable.ImmutableElement;
import io.durian.immutable.ImmutableNamespace;
import io.durian.immutable.ImmutableText;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.copyOfRange;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;

class SaxHandler extends DefaultHandler {
    Element root;
    final Map<String, String> prefixByUri = new HashMap<>();
    final Deque<Element> elementStack = new LinkedList<>();
    final Map<String, List<Content>> contentByElementId = new HashMap<>();

    @Override
    public void startPrefixMapping(String prefix, String uri) {
        prefixByUri.put(uri, prefix);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        List<Content> content = new ArrayList<>();
        List<Attribute> attributeList = new ArrayList<>();
        Element parent = elementStack.peek();
        Element element = new ImmutableElement(
                randomUUID().toString(),
                localName,
                () -> content,
                () -> attributeList,
                ofNullable(parent),
                ofNullable(namespace(uri))
        );
        if (root == null)
            root = element;
        if (parent != null)
            contentByElementId.get(parent.id()).add(element);
        elementStack.push(element);
        contentByElementId.put(element.id(), content);
        addAttributes(attributes, attributeList);
    }

    private void addAttributes(Attributes attributes, List<Attribute> attributeList) {
        for (int index = 0; index < attributes.getLength(); index++)
            attributeList.add(
                    new ImmutableAttribute(
                            randomUUID().toString(),
                            attributes.getLocalName(index),
                            attributes.getValue(index),
                            ofNullable(elementStack.peek()),
                            ofNullable(namespace(attributes.getURI(index)))
                    )
            );
    }

    private ImmutableNamespace namespace(String uri) {
        return ofNullable(uri)
                .map(prefixByUri::get)
                .map(prefix -> new ImmutableNamespace(uri, prefix))
                .orElse(null);
    }

    @Override
    public void characters(final char[] textArray, final int start, final int length) {
        final String textValue = new String(copyOfRange(textArray, start, start + length));
        if (!textValue.isBlank()) {
            Element element = elementStack.peek();
            if (element != null)
                contentByElementId.get(element.id()).add(new ImmutableText(
                                randomUUID().toString(),
                                textValue,
                                of(element)
                        )
                );
            else
                throw new IllegalStateException();
        }
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) {
        elementStack.pop();
    }

    @Override
    public void error(final SAXParseException e) throws SAXException {
        throw e;
    }

    @Override
    public void warning(final SAXParseException e) throws SAXException {
        throw e;
    }

}
