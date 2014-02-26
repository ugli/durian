package se.ugli.durian.j.dom.parser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.NodeFactory;

public final class ParserBuilder {

    private static SAXParser createSaxParser() {
        try {
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            final SAXParser parser = factory.newSAXParser();
            return parser;
        }
        catch (final ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        catch (final SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static ParserBuilder apply() {
        return new ParserBuilder();
    }

    private NodeFactory nodeFactory;
    private ErrorHandler errorHandler;
    private SAXParser saxParser;

    private ParserBuilder() {
        nodeFactory = new MutableNodeFactory();
        errorHandler = new DefaultErrorHandler();
        saxParser = createSaxParser();
    }

    public ParserBuilder nodeFactory(final NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
        return this;
    }

    public ParserBuilder saxParser(final SAXParser saxParser) {
        this.saxParser = saxParser;
        return this;
    }

    public ParserBuilder errorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    public Parser build() {
        return new Parser(nodeFactory, errorHandler, saxParser);
    }

}
