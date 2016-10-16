package se.ugli.durian.j.dom.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Optional;

import org.junit.Test;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.parser.Parser;

public class ElementImplTest {

    @Test
    public void elementText() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        assertEquals("Schema for Purchase Order Example", element.getElementText("title").get());
    }

    @Test
    public void selectClone() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final Optional<Element> elementClone = element.select().element("/schema/title").map(e -> e.clone().element());
        assertNotEquals(elementClone, element.getElements().iterator().next());
        assertEquals("Schema for Purchase Order Example", elementClone.get().getTexts().iterator().next().getValue());
    }

}
