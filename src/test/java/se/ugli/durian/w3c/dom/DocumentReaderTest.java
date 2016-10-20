package se.ugli.durian.w3c.dom;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import se.ugli.commons.Resource;
import se.ugli.durian.Durian;
import se.ugli.durian.j.dom.node.Element;

public class DocumentReaderTest {

    @Test
    public void test() throws ParserConfigurationException, SAXException, IOException {
        final String resource = "/PurchaseOrder.sch";

        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final Document document = documentBuilder.parse(Resource.apply(resource).getInputStream());

        final Element element = Durian.fromW3CDocument(document);

        assertEquals(Resource.apply(resource).getString(), element.toXml());
    }

}
