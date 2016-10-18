package se.ugli.durian.j.dom.query.jaxen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.parser.Parser;

public class QueryEngineTest {

    @Test
    public void absolutePath() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final List<Element> nodes = element.select().elements("/schema/pattern/rule");
        assertEquals(9, nodes.size());
    }

    @Test
    public void getChildAxisIteratorPath() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final List<Element> nodes = element.select().elements("//rule");
        assertEquals(9, nodes.size());
    }

    @Test
    public void relativePath() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final List<Element> nodes = element.select().elements("//rule");
        final Element firstRule = nodes.get(0);
        final Element firstPattern = firstRule.getParent().get();
        final List<? extends Node> assertNodes = firstPattern.select().nodes("rule/assert");
        assertEquals(5, assertNodes.size());
    }

    @Test
    public void ruleContexts() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final Attribute attribute0 = element.select().attribute("//@queryBinding").get();
        assertEquals("xpath2", attribute0.getValue());

    }

    @Test
    public void titleText() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final String text = element.select().text("/schema/title/text()").get();
        assertEquals("Schema for Purchase Order Example", text);

    }

    @Test
    public void evalateBoolean() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        assertTrue(element.select().evaluteBoolean("/schema/@queryBinding='xpath2'"));
        assertFalse(element.select().evaluteBoolean("/schema/@queryBinding='xpath3'"));
        assertFalse(element.select().evaluteBoolean("/schema/@queryBindings='xpath'"));
    }

}
