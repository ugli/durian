package se.ugli.durian.j.dom.mutable;

import static java.util.stream.Collectors.toList;

import java.util.Optional;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.AttributeCloneApi;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.utils.Id;

public class MutableAttribute implements Attribute, MutableNode {

    private final String name;
    private Optional<Element> parent = Optional.empty();
    private final Optional<String> uri;
    private String value;
    private final String id = Id.create();
    private final NodeFactory nodeFactory;

    public MutableAttribute(final String name, final String uri, final String value, final NodeFactory nodeFactory) {
        this.name = name;
        this.uri = Optional.ofNullable(uri);
        this.value = value;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public AttributeCloneApi clone() {
        return new MutableAttributeCloneApiImpl(this, nodeFactory);
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public NodeFactory nodeFactory() {
        return nodeFactory;
    }

    @Override
    public Optional<Element> parent() {
        return parent;
    }

    @Override
    public String path() {
        final String selfPath = "/@" + name;
        if (parent.isPresent())
            return parent.get().path() + selfPath;
        return selfPath;
    }

    private Optional<String> prefix(final String uri, final Element element) {
        for (final PrefixMapping prefixmapping : element.prefixMappings().collect(toList()))
            if (uri.equals(prefixmapping.uri))
                return prefixmapping.prefix;
        if (element.parent().isPresent())
            return prefix(uri, element.parent().get());
        return Optional.empty();
    }

    @Override
    public String qName() {
        if (uri.isPresent() && parent.isPresent()) {
            final Optional<String> prefix = prefix(uri.get(), parent.get());
            if (prefix.isPresent())
                return prefix.get() + ":" + name;
        }
        return name;
    }

    @Override
    public void setParent(final Element parent) {
        this.parent = Optional.ofNullable(parent);
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MutableAttribute [name=" + name + ", uri=" + uri + ", value=" + value + "]";
    }

    @Override
    public Optional<String> uri() {
        return uri;
    }

    @Override
    public String value() {
        return value;
    }

}
