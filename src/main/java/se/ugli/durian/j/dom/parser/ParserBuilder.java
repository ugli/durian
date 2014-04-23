package se.ugli.durian.j.dom.parser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.NodeFactory;

public final class ParserBuilder {

    public static ParserBuilder apply() {
        return new ParserBuilder();
    }

    private ErrorHandler errorHandler;
    private NodeFactory nodeFactory;
    private SAXParser saxParser;

    private ParserBuilder() {
    }

    public Parser build() {
        if (nodeFactory == null) {
            nodeFactory = new MutableNodeFactory();
        }
        if (errorHandler == null) {
            errorHandler = new DefaultErrorHandler();
        }
        if (saxParser == null) {
            saxParser = getDefaultSaxParser();
        }
        return new Parser(nodeFactory, errorHandler, saxParser);
    }

    public ParserBuilder errorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    public ParserBuilder nodeFactory(final NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
        return this;
    }

    public ParserBuilder saxParser(final SAXParser saxParser) {
        this.saxParser = saxParser;
        return this;
    }

    private SAXParser getDefaultSaxParser() {
        try {
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            return factory.newSAXParser();
        }
        catch (final ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        catch (final SAXException e) {
            throw new RuntimeException(e);
        }
    }

}
