package se.ugli.durian.j.dom.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Optional;

import org.junit.Test;

import se.ugli.commons.Resource;
import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;

public class ElementImplTest {

    @Test
    public void elementText() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        assertEquals("Schema for Purchase Order Example", element.elementText("title").get());
    }

    @Test
    public void selectClone() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        final Optional<Element> elementClone = element.select().element("/schema/title").map(e -> e.clone().element());
        assertNotEquals(elementClone, element.elements().iterator().next());
        assertEquals("Schema for Purchase Order Example", elementClone.get().texts().iterator().next().value());
    }

}
