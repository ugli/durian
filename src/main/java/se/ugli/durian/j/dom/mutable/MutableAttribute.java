package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;

public class MutableAttribute implements Attribute, MutableNode {

    private final String name;
    private Element parent;
    private final String uri;
    private String value;
    private NodeFactory nodeFactory;

    public MutableAttribute(final String name, final String uri, final String value, final NodeFactory nodeFactory) {
        this.name = name;
        this.uri = uri;
        this.value = value;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }

    @Override
    public <T extends Attribute> T cloneAttribute() {
        return cloneAttribute(name, nodeFactory);
    }

    @Override
    public <T extends Attribute> T cloneAttribute(final NodeFactory nodeFactory) {
        return cloneAttribute(name, nodeFactory);
    }

    @Override
    public <T extends Attribute> T cloneAttribute(final String attributeName) {
        return cloneAttribute(attributeName, nodeFactory);
    }

    @Override
    public <T extends Attribute> T cloneAttribute(final String attributeName, final NodeFactory nodeFactory) {
        return nodeFactory.createAttribute(attributeName, uri, null, value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MutableAttribute other = (MutableAttribute) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Element> T getParent() {
        return (T) parent;
    }

    @Override
    public String getPath() {
        return parent.getPath() + "/@" + name;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public void setParent(final Element parent) {
        this.parent = parent;
    }

    @Override
    public void setNodeFactory(final NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

}
