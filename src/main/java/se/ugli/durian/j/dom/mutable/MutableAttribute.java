package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;

public class MutableAttribute implements Attribute {

    private final String value;
    private final Element parent;
    private final String name;
    private final String uri;

    public MutableAttribute(final String name, final String uri, final Element parent, final String value) {
        this.name = name;
        this.uri = uri;
        this.parent = parent;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
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
    public Element getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        return parent.getPath() + "/@" + name;
    }

}
