package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.AttributeCloneApi;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;

class MutableAttributeApiImpl implements AttributeCloneApi {

    private final Element parent;
    private final NodeFactory nodeFactory;
    private final String name;
    private final String uri;
    private final String value;

    public MutableAttributeApiImpl(final MutableAttribute attribute, final NodeFactory nodeFactory) {
        this.parent = null;
        this.name = attribute.getName();
        this.uri = attribute.getUri().orElse(null);
        this.value = attribute.getValue();
        this.nodeFactory = nodeFactory;
    }

    @Override
    public <T extends Attribute> T attribute() {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public <T extends Attribute> T attribute(final Element parent) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public <T extends Attribute> T attribute(final NodeFactory nodeFactory) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public <T extends Attribute> T attribute(final Element parent, final NodeFactory nodeFactory) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public <T extends Attribute> T attribute(final String attributeName) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public <T extends Attribute> T attribute(final Element parent, final String attributeName) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public <T extends Attribute> T attribute(final String attributeName, final NodeFactory nodeFactory) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public <T extends Attribute> T attribute(final Element parent, final String attributeName, final NodeFactory nodeFactory) {
        return nodeFactory.createAttribute(name, uri, parent, value);
    }

}