package io.durian.immutable;

import io.durian.model.Content;
import io.durian.model.DurianException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static java.util.Arrays.copyOfRange;
import static java.util.Optional.ofNullable;

public class SaxParser {

	public static Element parse(Reader reader) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser saxParser = factory.newSAXParser();
			SaxHandler saxHandler = new SaxHandler();
			saxParser.parse(new InputSource(reader), saxHandler);
			return saxHandler.root;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new DurianException(e);
		}
	}

	static class SaxHandler extends DefaultHandler {
		private Element root;
		private final Map<String, String> prefixByUri = new HashMap<>();
		private final Deque<Element> elementStack = new LinkedList<>();
		private final Map<String, List<Content>> contentByElementId = new HashMap<>();

		@Override
		public void startPrefixMapping(String prefix, String uri) {
			prefixByUri.put(uri, prefix);
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			List<Content> content = new ArrayList<>();
			List<Attribute> attributeList = new ArrayList<>();
			Element parent = elementStack.peek();
			Element element = new Element(
					localName,
					() -> content,
					() -> attributeList,
					parent,
					namespace(uri)
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
						new Attribute(
								attributes.getLocalName(index),
								attributes.getValue(index),
								elementStack.peek(),
								namespace(attributes.getURI(index))
						)
				);
		}

		private Namespace namespace(String uri) {
			return ofNullable(uri)
					.map(prefixByUri::get)
					.map(prefix -> new Namespace(uri, prefix))
					.orElse(null);
		}

		@Override
		public void characters(final char[] textArray, final int start, final int length) {
			final String textValue = new String(copyOfRange(textArray, start, start + length));
			if (!textValue.isBlank()) {
				Element element = elementStack.peek();
				if (element != null)
					contentByElementId.get(element.id()).add(new Text(textValue, element));
				else
					throw new IllegalStateException();
			}
		}

		@Override
		public void endElement(final String uri, final String localName, final String qName) {
			elementStack.pop();
		}

		@Override
		public void fatalError(final SAXParseException e) throws SAXException {
			throw e;
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


}