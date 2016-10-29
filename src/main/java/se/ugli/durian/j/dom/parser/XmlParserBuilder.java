package se.ugli.durian.j.dom.parser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.NodeFactory;

public class XmlParserBuilder {

    public static XmlParserBuilder apply() {
        return new XmlParserBuilder();
    }

    protected ErrorHandler errorHandler;
    protected NodeFactory nodeFactory;
    protected SAXParser saxParser;

    protected XmlParserBuilder() {
    }

    public XmlParser build() {
        if (nodeFactory == null)
            nodeFactory = new MutableNodeFactory();
        if (errorHandler == null)
            errorHandler = new DefaultErrorHandler();
        if (saxParser == null)
            saxParser = getDefaultSaxParser();
        return new XmlParser(nodeFactory, errorHandler, saxParser);
    }

    public XmlParserBuilder errorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    public XmlParserBuilder nodeFactory(final NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
        return this;
    }

    public XmlParserBuilder saxParser(final SAXParser saxParser) {
        this.saxParser = saxParser;
        return this;
    }

    protected static SAXParser getDefaultSaxParser() {
        try {
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            return factory.newSAXParser();
        }
        catch (final ParserConfigurationException e) {
            throw new XmlParserException(e);
        }
        catch (final SAXException e) {
            throw new XmlParserException(e);
        }
    }

}
