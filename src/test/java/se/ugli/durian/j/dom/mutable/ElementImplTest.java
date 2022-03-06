package se.ugli.durian.j.dom.mutable;

import org.junit.jupiter.api.Test;
import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.java.io.Resource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
