package se.ugli.durian.w3c.soap;

import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.parser.DefaultErrorHandler;
import se.ugli.durian.j.dom.parser.XmlParser;
import se.ugli.durian.j.dom.parser.XmlParserBuilder;

public class SoapParserBuilder extends XmlParserBuilder {

	public static SoapParserBuilder apply() {
		return new SoapParserBuilder();
	}

	@Override
	public XmlParser build() {
		if (nodeFactory == null)
			nodeFactory = new MutableNodeFactory();
		if (errorHandler == null)
			errorHandler = new DefaultErrorHandler();
		if (saxParser == null)
			saxParser = getDefaultSaxParser();
		return new XmlParser(new SoapNodeFactory(nodeFactory), errorHandler, saxParser);
	}

}
