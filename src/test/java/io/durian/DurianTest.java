package io.durian;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

class DurianTest {

    @Test
    void wrapW3c() throws ParserConfigurationException, IOException, SAXException {
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
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
        Element element = Durian.wrap(document);
        assertEquals(xml, element.toXml());
    }

    @Test
    void parseHtml() {
        Element element = Durian.parseHtml(getClass().getResourceAsStream("/R_3543-0295.04DG.html"), UTF_8);
        List<Node> select = element.select("//*:div[@class='rawcontent']/*:div[1]");
        assertEquals(1, select.size());
        Element div = select.get(0).asElement();
        String divHtml = """
                <div xmlns="http://www.w3.org/1999/xhtml" class="jamn">
                   <span class="StorRubrik indent">RÄGN </span>
                   <span class="StorKursiv">räŋ</span>
                   <span class="upphojd">4</span>
                   <span class="StorKursiv">n, </span>
                   <span class="LitenAntikva">n.; best. </span>
                   <span class="Formstil">-et; </span>
                   <span class="LitenAntikva">pl. =.</span>
                </div>
                """;
        assertEquals(divHtml, div.toString());
    }

    @Test
    void parseXml() {
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
        Element element = Durian.parseXml(new StringReader(xml));
        assertEquals(xml, element.toXml());
    }


    @Test
    void saxonSelectElements() {
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
        Element rot = Durian.parseXml(new StringReader(xml));
        List<Node> elements = rot.select("//barn");
        assertEquals(2, elements.size());

        Element barn1 = elements.get(0).asElement();
        assertEquals("barn", barn1.name());
        assertEquals(0, barn1.attributes().size());

        Element barn2 = elements.get(1).asElement();
        assertEquals("barn", barn2.name());
        assertEquals("1", barn2.attributes().stream().filter(a -> a.name().equals("x")).map(Attribute::value).findFirst().orElse(null));
        assertEquals("3", barn2.attributes().stream().filter(a -> a.name().equals("y")).map(Attribute::value).findFirst().orElse(null));

        assertSame(rot.content().get(0), barn1);
        assertSame(rot.content().get(1), barn2);
    }

    @Test
    void saxonSelectElements2() {
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
        Element rot = Durian.parseXml(new StringReader(xml));
        List<Node> elements = rot.select("//barn");
        assertEquals(2, elements.size());

        Element barn1 = elements.get(0).asElement();
        assertEquals("barn", barn1.name());
        assertEquals(0, barn1.attributes().size());

        Element barn2 = elements.get(1).asElement();
        assertEquals("barn", barn2.name());
        assertEquals("1", barn2.attributes().stream().filter(a -> a.name().equals("x")).map(Attribute::value).findFirst().orElse(null));
        assertEquals("3", barn2.attributes().stream().filter(a -> a.name().equals("y")).map(Attribute::value).findFirst().orElse(null));

        assertSame(rot.content().get(0), barn1);
        assertSame(rot.content().get(1), barn2);
    }



}