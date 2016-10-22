package se.ugli.durian.j.dom.parser;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import javax.xml.parsers.SAXParser;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;

public class Parser {

    @FunctionalInterface
    private interface ParseCommand {
        void exec() throws IOException, SAXException;
    }

    public static Parser apply() {
        return ParserBuilder.apply().build();
    }

    private final SAXParser saxParser;
    private final SaxHandler saxHandler;

    Parser(final NodeFactory nodeFactory, final ErrorHandler errorHandler, final SAXParser saxParser) {
        this.saxParser = saxParser;
        saxHandler = new SaxHandler(nodeFactory, errorHandler);
    }

    public Element parse(final Source source) {
        return parse(source.data());
    }

    public Element parse(final InputStream stream) {
        return parse(() -> saxParser.parse(stream, saxHandler));
    }

    public Element parse(final Reader reader) {
        return parse(new InputSource(reader));
    }

    public Element parse(final InputSource source) {
        return parse(() -> saxParser.parse(source, saxHandler));
    }

    public Element parse(final File file) {
        return parse(() -> saxParser.parse(file, saxHandler));
    }

    public Element parseUri(final String uri) {
        return parse(() -> saxParser.parse(uri, saxHandler));
    }

    private Element parse(final ParseCommand parseCmd) {
        try {
            parseCmd.exec();
            return saxHandler.root;
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        catch (final SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public Element parse(final char[] chars) {
        return parse(new CharArrayReader(chars));
    }

    public Element parse(final byte[] bytes) {
        return parse(new ByteArrayInputStream(bytes));
    }

    public Element parseResource(final String resource) {
        try (InputStream resourceAsStream = getClass().getResourceAsStream(resource)) {
            if (resourceAsStream != null)
                return parse(resourceAsStream);
            throw new RuntimeException("Resource not found: " + resource);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Element parse(final URL url) {
        try (InputStream inputStream = url.openStream()) {
            return parse(inputStream);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}