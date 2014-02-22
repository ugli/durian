package se.ugli.durian.j.dom.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Name;
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
        final List<Attribute> result = new ArrayList<Attribute>();
        for (int index = 0; index < attributes.getLength(); index++) {
            result.add(createAttribute(index));
        }
        return result;
    }

    Attribute createAttribute(final int index) {
        return nodeFactory.createAttribute(parent, createName(index), getValue(index));
    }

    String getValue(final int index) {
        return attributes.getValue(index);
    }

    Name createName(final int index) {
        return nodeFactory.createName(attributes.getURI(index), attributes.getLocalName(index),
                attributes.getQName(index));
    }

}