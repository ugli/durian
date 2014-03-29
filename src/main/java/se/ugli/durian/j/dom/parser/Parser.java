package se.ugli.durian.j.dom.parser;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.transform.Source;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;

public class Parser {

    public static Parser apply() {
        return ParserBuilder.apply().build();
    }

    private final SAXParser saxParser;
    private final SaxHandler saxHandler;

    Parser(final NodeFactory nodeFactory, final ErrorHandler errorHandler, final SAXParser saxParser) {
        this.saxParser = saxParser;
        saxHandler = new SaxHandler(nodeFactory, errorHandler);
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T parse(final InputStream stream) {
        try {
            saxParser.parse(stream, saxHandler);
            return (T) saxHandler.root;
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        catch (final SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Element> T parse(final Reader reader) {
        return parse(new InputSource(reader));
    }

    public <T extends Element> T parse(final char[] chars) {
        return parse(new CharArrayReader(chars));
    }

    public <T extends Element> T parse(final byte[] bytes) {
        return parse(new ByteArrayInputStream(bytes));
    }

    public <T extends Element> T parseResource(final String resource) {
        final InputStream resourceAsStream = getClass().getResourceAsStream(resource);
        if (resourceAsStream != null) {
            return parse(resourceAsStream);
        }
        throw new RuntimeException("Resource not found: " + resource);
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T parse(final File file) {
        try {
            saxParser.parse(file, saxHandler);
            return (T) saxHandler.root;
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        catch (final SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T parseUri(final String uri) {
        try {
            saxParser.parse(uri, saxHandler);
            return (T) saxHandler.root;
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        catch (final SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Element> T parse(final URL url) {
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            return parse(inputStream);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T parse(final InputSource source) {
        try {
            saxParser.parse(source, saxHandler);
            return (T) saxHandler.root;
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        catch (final SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    public Element parse(final Source source) {
        // TODO
        throw new UnsupportedOperationException("TBD");
    }

}