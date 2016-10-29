package se.ugli.durian.j.dom.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;

class AttributesFactory {

    private final NodeFactory nodeFactory;
    private final Attributes attributes;
    private final Element parent;

    AttributesFactory(final NodeFactory nodeFactory, final Element parent, final Attributes attributes) {
        this.nodeFactory = nodeFactory;
        this.parent = parent;
        this.attributes = attributes;
    }

    List<Attribute> create() {
        final List<Attribute> result = new ArrayList<>();
        for (int index = 0; index < attributes.getLength(); index++)
            result.add(createAttribute(index));
        return result;
    }

    private Attribute createAttribute(final int index) {
        final String name = attributes.getLocalName(index);
        final String uri = attributes.getURI(index);
        final String value = attributes.getValue(index);
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

}