package io.durian.sax;

import io.durian.Element;
import lombok.SneakyThrows;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.Reader;

public class SaxParser {

    @SneakyThrows
    public static Element parse(Reader reader) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser saxParser = factory.newSAXParser();
        SaxHandler saxHandler = new SaxHandler();
        saxParser.parse(new InputSource(reader), saxHandler);
        return saxHandler.root;
    }


}