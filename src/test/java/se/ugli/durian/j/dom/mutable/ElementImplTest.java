package se.ugli.durian.j.dom.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.parser.Parser;

public class ElementImplTest {

    @Test
    public void elementText() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        assertEquals("Schema for Purchase Order Example", element.getElementText("title"));
    }

    @Test
    public void selectClone() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final Element elementClone = element.select().element("/schema/title").clone().element();
        assertNotEquals(elementClone, element.getElements().iterator().next());
        assertEquals("Schema for Purchase Order Example", elementClone.getTexts().iterator().next().getValue());
    }

}
