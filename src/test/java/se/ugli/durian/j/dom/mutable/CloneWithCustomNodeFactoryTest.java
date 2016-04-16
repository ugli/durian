package se.ugli.durian.j.dom.mutable;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Prefixmapping;
import se.ugli.durian.j.dom.parser.Parser;
import se.ugli.durian.j.dom.parser.ParserBuilder;

public class CloneWithCustomNodeFactoryTest {

    class TitleElement extends MutableElement {

        public TitleElement(final String name, final String uri, final NodeFactory nodeFactory) {
            super(name, uri, nodeFactory, new ArrayList<Prefixmapping>());
        }

    }

    class QueryBindingAttribute extends MutableAttribute {

        public QueryBindingAttribute(final String name, final String uri, final String value) {
            super(name, uri, value);
        }

    }

    class TestNodeFactory extends MutableNodeFactory {

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Element> T createElement(final String name, final String uri, final Element parent,
                final Iterable<Prefixmapping> prefixmappings) {
            if (parent != null && parent.getName().equals("schema") && name.equals("title"))
                return (T) new TitleElement(name, uri, this);
            return super.createElement(name, uri, parent, prefixmappings);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Attribute> T createAttribute(final String name, final String uri, final Element parent, final String value) {
            if (parent != null && parent.getName().equals("schema") && name.equals("queryBinding"))
                return (T) new QueryBindingAttribute(name, uri, value);
            return super.createAttribute(name, uri, parent, value);
        }
    }

    @Test
    public void test() {

        final Parser parser = ParserBuilder.apply().nodeFactory(new TestNodeFactory()).build();
        final Element schema = parser.parseResource("/PurchaseOrder.sch");
        assertTrue(schema.selectElement("//title") instanceof TitleElement);
        assertTrue(schema.selectAttribute("//@queryBinding") instanceof QueryBindingAttribute);
        final Element schemaClone = schema.cloneElement();
        assertTrue(schemaClone.selectElement("//title") instanceof TitleElement);
        assertTrue(schemaClone.selectAttribute("//@queryBinding") instanceof QueryBindingAttribute);
    }

}
