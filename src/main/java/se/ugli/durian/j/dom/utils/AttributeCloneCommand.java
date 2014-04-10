package se.ugli.durian.j.dom.utils;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;

public final class AttributeCloneCommand {

    private AttributeCloneCommand() {

    }

    public static <T extends Attribute> T execute(final Attribute attributeToClone, final Element parent,
            final NodeFactory nodeFactory) {
        final String name = attributeToClone.getName();
        final String uri = attributeToClone.getUri();
        final String value = attributeToClone.getValue();
        return nodeFactory.createAttribute(name, uri, parent, value);
    }
}
