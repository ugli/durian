package se.ugli.durian.j.dom.parser;

import static se.ugli.durian.j.dom.node.PrefixMapping.prefixMapping;
import static se.ugli.java.lang.Strings.nonEmptyOrNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;

class SaxHandler extends DefaultHandler {

	private final Deque<MutableElement> elementStack = new LinkedList<>();
	private final List<PrefixMapping> prefixMappings = new LinkedList<>();
	private final NodeFactory nodeFactory;
	private final ErrorHandler errorHandler;
	Element root;

	SaxHandler(final NodeFactory nodeFactory, final ErrorHandler errorHandler) {
		this.nodeFactory = nodeFactory;
		this.errorHandler = errorHandler;
	}

	@Override
	public void startPrefixMapping(final String _prefix, final String _uri) {
		final String uri = nonEmptyOrNull(_uri);
		if (uri != null)
			prefixMappings.add(prefixMapping(nonEmptyOrNull(_prefix), uri));
	}

	@Override
	public void startElement(final String _uri, final String localName, final String qName,
			final Attributes saxAttributes) {
		final MutableElement parent = elementStack.isEmpty() ? null : elementStack.peek();
		final String uri = nonEmptyOrNull(_uri);
		final MutableElement element = nodeFactory
				.createElement(localName, uri, parent, new ArrayList<>(prefixMappings)).as(MutableElement.class);
		prefixMappings.clear();
		for (final Attribute attribute : new AttributesFactory(nodeFactory, element, saxAttributes).create())
			element.add(attribute);
		elementStack.push(element);
		if (parent != null)
			parent.add(element);
		else
			root = element;
	}

	@Override
	public void endElement(final String uri, final String localName, final String qName) {
		elementStack.pop();
	}

	@Override
	public void characters(final char ch[], final int start, final int length) {
		final String str = nonEmptyOrNull(new String(Arrays.copyOfRange(ch, start, start + length)));
		if (str != null)
			elementStack.peek().addText(str);
	}

	@Override
	public void fatalError(final SAXParseException e) throws SAXException {
		errorHandler.fatalError(e);
	}

	@Override
	public void error(final SAXParseException e) throws SAXException {
		errorHandler.error(e);
	}

	@Override
	public void warning(final SAXParseException e) throws SAXException {
		errorHandler.warning(e);
	}

}
