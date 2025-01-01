package io.durian.sax;

import io.durian.DurianException;
import io.durian.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.Reader;

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


}