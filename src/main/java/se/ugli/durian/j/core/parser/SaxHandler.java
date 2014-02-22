package se.ugli.durian.j.core.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import se.ugli.durian.j.core.Attribute;
import se.ugli.durian.j.core.Document;
import se.ugli.durian.j.core.Element;
import se.ugli.durian.j.core.Name;
import se.ugli.durian.j.core.NodeFactory;
import se.ugli.durian.j.core.PrefixMapping;

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
        final List<Attribute> attributes = new AttributesFactory(nodeFactory, saxAttributes).create();
        final Element element = nodeFactory.createElement(name, document, parent, attributes);
        stack.push(element);
        if (parent != null) {
            parent.append(element);
        }
        else {
            document.setRoot(element);
        }
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) {
        stack.pop();
    }

    @Override
    public void characters(final char ch[], final int start, final int length) {
        final String str = new String(Arrays.copyOfRange(ch, start, start + length));
        final String trimedStr = str.trim();
        if (trimedStr.length() > 0) {
            stack.peek().append(nodeFactory.createText(trimedStr));
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
