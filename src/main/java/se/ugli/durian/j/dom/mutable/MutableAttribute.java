package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.AttributeCloner;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Prefixmapping;
import se.ugli.durian.j.dom.utils.Id;

public class MutableAttribute implements Attribute, MutableNode {

    private final String name;
    private Element parent;
    private final String uri;
    private String value;
    private final String id = Id.create();
    private final NodeFactory nodeFactory;

    @Override
    public String id() {
        return id;
    }

    public MutableAttribute(final String name, final String uri, final String value, final NodeFactory nodeFactory) {
        this.name = name;
        this.uri = uri;
        this.value = value;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public NodeFactory nodeFactory() {
        return nodeFactory;
    }

    @Override
    public AttributeCloner clone() {
        return new MutableAttributeCloner(this, nodeFactory);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MutableAttribute other = (MutableAttribute) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
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
        if (parent != null)
            return parent.getPath() + "/@" + name;
        return null;
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
    public String toString() {
        return "MutableAttribute [name=" + name + ", uri=" + uri + ", value=" + value + "]";
    }

    private String prefix(final String uri, final Element element) {
        for (final Prefixmapping prefixmapping : element.prefixmappings())
            if (uri.equals(prefixmapping.uri))
                return prefixmapping.prefix;
        if (element.getParent() != null)
            return prefix(uri, element.getParent());
        return null;
    }

    @Override
    public String qName() {
        if (uri != null && parent != null) {
            final String prefix = prefix(uri, parent);
            if (prefix != null)
                return prefix + ":" + name;
        }
        return name;
    }

}
