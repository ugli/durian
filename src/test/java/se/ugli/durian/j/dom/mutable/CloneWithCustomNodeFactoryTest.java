package se.ugli.durian.j.dom.mutable;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.parser.Parser;
import se.ugli.durian.j.dom.parser.ParserBuilder;

public class CloneWithCustomNodeFactoryTest {

    class TitleElement extends MutableElement {

        public TitleElement(final String name, final String uri, final NodeFactory nodeFactory) {
            super(name, uri, nodeFactory, new ArrayList<PrefixMapping>());
        }

    }

    class QueryBindingAttribute extends MutableAttribute {

        public QueryBindingAttribute(final String name, final String uri, final String value, final NodeFactory nodeFactory) {
            super(name, uri, value, nodeFactory);
        }

    }

    class TestNodeFactory extends MutableNodeFactory {

        @Override
        public MutableElement createElement(final String name, final String uri, final Element parent,
                final Iterable<PrefixMapping> prefixmappings) {
            if (parent != null && parent.name().equals("schema") && name.equals("title"))
                return new TitleElement(name, uri, this);
            return super.createElement(name, uri, parent, prefixmappings);
        }

        @Override
        public MutableAttribute createAttribute(final String name, final String uri, final Element parent, final String value) {
            if (parent != null && parent.name().equals("schema") && name.equals("queryBinding"))
                return new QueryBindingAttribute(name, uri, value, this);
            return super.createAttribute(name, uri, parent, value);
        }
    }

    @Test
    public void test() {

        final Parser parser = ParserBuilder.apply().nodeFactory(new TestNodeFactory()).build();
        final Element schema = parser.parseResource("/PurchaseOrder.sch");
        assertTrue(schema.select().element("//title").get() instanceof TitleElement);
        assertTrue(schema.select().attribute("//@queryBinding").get() instanceof QueryBindingAttribute);
        final Element schemaClone = schema.clone().element();
        assertTrue(schemaClone.select().element("//title").get() instanceof TitleElement);
        assertTrue(schemaClone.select().attribute("//@queryBinding").get() instanceof QueryBindingAttribute);
    }

}
