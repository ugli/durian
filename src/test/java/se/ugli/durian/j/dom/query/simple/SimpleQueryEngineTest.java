package se.ugli.durian.j.dom.query.simple;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.parser.Parser;

public class SimpleQueryEngineTest {

    @Test
    public void absolutePath() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final List<? extends Node> nodes = element.selectNodes("/schema/pattern/rule");
        assertEquals(9, nodes.size());
    }

    @Test
    public void superPath() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final List<? extends Node> nodes = element.selectNodes("//rule");
        assertEquals(9, nodes.size());
    }

    @Test
    public void relativePath() {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        final List<? extends Node> nodes = element.selectNodes("//rule");
        final Node firstRule = nodes.get(0);
        final Element firstPattern = firstRule.getParent();
        final List<? extends Node> assertNodes = firstPattern.selectNodes("rule/assert");
        assertEquals(5, assertNodes.size());
    }
}
