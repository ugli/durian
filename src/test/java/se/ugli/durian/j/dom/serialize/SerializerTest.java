package se.ugli.durian.j.dom.serialize;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.xml.sax.SAXException;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.parser.Parser;

public class SerializerTest {

    @Test
    public void test() throws SAXException, IOException, DocumentException {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final String durianXml = Serializer.apply().serialize(element);

        final SAXReader saxReader = new SAXReader();
        final Document document = saxReader.read(getClass().getResourceAsStream("/PurchaseOrder.sch"));
        final StringWriter stringWriter = new StringWriter();
        final OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        final XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
        xmlWriter.write(document);
        final String dom4jXml = stringWriter.toString();

        assertEquals(dom4jXml, durianXml);
    }

}
