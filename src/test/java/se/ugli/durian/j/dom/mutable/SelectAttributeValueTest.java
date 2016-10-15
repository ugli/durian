package se.ugli.durian.j.dom.mutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.parser.Parser;
import se.ugli.durian.j.dom.query.QueryException;

public class SelectAttributeValueTest {

    @Test
    public void queryBinding() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        assertEquals("xpath2", element.select().attributeValue("/schema/@queryBinding"));
    }

    @Test
    public void notFound() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        assertNull(element.select().attributeValue("/schema/@queryBindings"));
    }

    @Test(expected = QueryException.class)
    public void boom() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        assertNull(element.select().attributeValue("//@test"));
    }
}
