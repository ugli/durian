package se.ugli.durian.j.dom.parser;

import java.util.Arrays;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Name;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.node.Text;

class SaxHandler extends DefaultHandler {

    final Document document;
    private final Stack<Element> stack = new Stack<Element>();
    private final NodeFactory nodeFactory;

    public SaxHandler(final NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
        document = nodeFactory.createDocument();
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName,
            final Attributes saxAttributes) {
        final Name name = nodeFactory.createName(uri, localName, qName);
        final Element parent = stack.isEmpty() ? null : stack.peek();
        final Element element = nodeFactory.createElement(document, parent, name);
        for (final Attribute attribute : new AttributesFactory(nodeFactory, element, saxAttributes).create()) {
            element.getAttributes().add(attribute);
        }
        stack.push(element);
        if (parent != null) {
            parent.getContent().add(element);
        }
        else {
            document.setRoot(element);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void endElement(final String uri, final String localName, final String qName) {
        stack.pop();
    }

    @Override
    public void characters(final char ch[], final int start, final int length) {
        final Element element = stack.peek();
        if (element.isSupportsText()) {
            final String str = new String(Arrays.copyOfRange(ch, start, start + length));
            final String trimedStr = str.trim();
            if (trimedStr.length() > 0) {
                final Text text = nodeFactory.createText(element, trimedStr);
                element.getContent().add(text);
            }
        }
        else {
            // TODO log warn
        }
    }

    @Override
    public void startPrefixMapping(final String prefix, final String uri) {
        final String prefixToCreate = prefix != null && !prefix.trim().isEmpty() ? prefix : null;
        final PrefixMapping prefixMapping = nodeFactory.createPrefixMapping(prefixToCreate, uri);
        document.add(prefixMapping);
    }

    @Override
    public void fatalError(final SAXParseException e) throws SAXParseException {
        throw e;
    }

    @Override
    public void error(final SAXParseException e) throws SAXParseException {
        throw e;
    }

    @Override
    public void warning(final SAXParseException e) throws SAXParseException {
        throw e;
    }

}
