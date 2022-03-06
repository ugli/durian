package se.ugli.durian.j.dom.query.jaxen;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.java.io.Resource;

public class QueryEngineTest {

    @Test
    public void absolutePath() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        final List<Element> nodes = element.select().elements("/schema/pattern/rule").collect(toList());
        assertEquals(9, nodes.size());
    }

    @Test
    public void getChildAxisIteratorPath() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        final List<Element> nodes = element.select().elements("//rule").collect(toList());
        assertEquals(9, nodes.size());
    }

    @Test
    public void relativePath() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        final List<Element> nodes = element.select().elements("//rule").collect(toList());
        final Element firstRule = nodes.get(0);
        final Element firstPattern = firstRule.parent().get();
        final List<Node> assertNodes = firstPattern.select().nodes("rule/assert").collect(toList());
        assertEquals(5, assertNodes.size());
    }

    @Test
    public void ruleContexts() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        final Attribute attribute0 = element.select().attribute("//@queryBinding").get();
        assertEquals("xpath2", attribute0.value());

    }

    @Test
    public void titleText() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        final String text = element.select().text("/schema/title/text()").get();
        assertEquals("Schema for Purchase Order Example", text);

    }

    @Test
    public void evalateBoolean() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        assertTrue(element.select().boolValue("/schema/@queryBinding='xpath2'"));
        assertFalse(element.select().boolValue("/schema/@queryBinding='xpath3'"));
        assertFalse(element.select().boolValue("/schema/@queryBindings='xpath'"));
    }

    @Test
    public void evalateLong() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        assertThat(element.select().longValue("count(//assert)"), is(19L));
    }

    @Test
    public void evalateDouble() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        assertThat(element.select().doubleValue("count(//assert)"), is(19D));
    }

}