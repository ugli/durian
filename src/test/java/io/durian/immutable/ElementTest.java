package io.durian.immutable;

import io.durian.Node;
import io.durian.Element;
import io.durian.Attribute;
import io.durian.sax.SaxParser;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementTest {

    @Test
    void jaxenSelectElements() {
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
        Element element = SaxParser.parse(new StringReader(xml));
        List<Node> elements = element.select("//barn");
        assertEquals(2, elements.size());

        Element element1 = elements.get(0).asElement();
        assertEquals("barn", element1.name());
        assertEquals(0, element1.attributes().size());

        Element element2 = elements.get(1).asElement();
        assertEquals("barn", element2.name());
        assertEquals("1", element2.attributes().stream().filter(a -> a.name().equals("x")).map(Attribute::value).findFirst().orElse(null));
        assertEquals("3", element2.attributes().stream().filter(a -> a.name().equals("y")).map(Attribute::value).findFirst().orElse(null));
    }

}