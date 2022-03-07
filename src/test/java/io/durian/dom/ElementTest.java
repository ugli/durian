package io.durian.dom;

import io.durian.immutable.Attribute;
import io.durian.immutable.SaxParser;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElementTest {

    @Test
    void parseTest() throws ParserConfigurationException, IOException, SAXException {
        String xml = """
                <rot>
                  <barn>
                    <barnbarn a="11"/>
                  </barn>
                  <barn x="1" y="3">
                    <barnbarn bbb="qwqw"/>
                  </barn>
                </rot>
                """;
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        Element element = new Element(document);
        assertEquals(xml.trim(), element.toString());
    }

}