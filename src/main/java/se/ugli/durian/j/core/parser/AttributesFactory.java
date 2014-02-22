package se.ugli.durian.j.core.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import se.ugli.durian.j.core.Attribute;
import se.ugli.durian.j.core.Name;
import se.ugli.durian.j.core.NodeFactory;

class AttributesFactory {

    private final NodeFactory nodeFactory;
    private final Attributes attributes;

    AttributesFactory(final NodeFactory nodeFactory, final Attributes attributes) {
        this.nodeFactory = nodeFactory;
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
        return nodeFactory.createAttribute(createName(index), getValue(index));
    }

    String getValue(final int index) {
        return attributes.getValue(index);
    }

    Name createName(final int index) {
        return nodeFactory.createName(attributes.getURI(index), attributes.getLocalName(index),
                attributes.getQName(index));
    }

}