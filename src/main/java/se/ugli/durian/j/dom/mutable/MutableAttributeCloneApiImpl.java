package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.AttributeCloneApi;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;

class MutableAttributeCloneApiImpl implements AttributeCloneApi {

    private final Element parent;
    private final NodeFactory nodeFactory;
    private final String name;
    private final String uri;
    private final String value;

    public MutableAttributeCloneApiImpl(final MutableAttribute attribute, final NodeFactory nodeFactory) {
        this.parent = null;
        this.name = attribute.name();
        this.uri = attribute.uri().orElse(null);
        this.value = attribute.value();
        this.nodeFactory = nodeFactory;
    }

    @Override
    public Attribute attribute() {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public Attribute attribute(final Element parent) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public Attribute attribute(final Element parent, final NodeFactory nodeFactory) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public Attribute attribute(final Element parent, final String attributeName) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public Attribute attribute(final Element parent, final String attributeName, final NodeFactory nodeFactory) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public Attribute attribute(final NodeFactory nodeFactory) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public Attribute attribute(final String attributeName) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public Attribute attribute(final String attributeName, final NodeFactory nodeFactory) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

}