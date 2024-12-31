package io.durian.saxon;

import io.durian.dom.Attribute;
import io.durian.dom.Element;
import io.durian.dom.Node;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElementTest {

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
        Element rot = SaxonParser.parse(new StringReader(xml));
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