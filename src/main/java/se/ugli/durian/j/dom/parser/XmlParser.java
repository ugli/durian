package se.ugli.durian.j.dom.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.SAXParser;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;

public class XmlParser {

    private final SAXParser saxParser;
    private final SaxHandler saxHandler;

    public XmlParser(final NodeFactory nodeFactory, final ErrorHandler errorHandler, final SAXParser saxParser) {
        this.saxParser = saxParser;
        saxHandler = new SaxHandler(nodeFactory, errorHandler);
    }

    public Element parse(final Source source) {
        try {
            saxParser.parse(new ByteArrayInputStream(source.data()), saxHandler);
            return saxHandler.root;
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        catch (final SAXException e) {
            throw new RuntimeException(e);
        }
    }

}