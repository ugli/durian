package se.ugli.durian.j.core.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import se.ugli.durian.j.core.Document;
import se.ugli.durian.j.core.NodeFactory;

public class Parser {

    public static Parser apply(final NodeFactory nodeFactory) {
        return new Parser(nodeFactory);
    }

    private final SAXParser saxParser;
    private final SaxHandler saxHandler;

    private Parser(final NodeFactory nodeFactory) {
        saxParser = createSaxParser();
        saxHandler = new SaxHandler(nodeFactory);
    }

    public Document parse(final InputStream stream) {
        try {
            saxParser.parse(stream, saxHandler);
            return saxHandler.document;
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        catch (final SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private SAXParser createSaxParser() {
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

}