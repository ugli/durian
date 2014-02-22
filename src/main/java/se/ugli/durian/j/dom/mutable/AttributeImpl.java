package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Name;

public class AttributeImpl implements Attribute {

    private final Name name;
    private final String value;
    private final Element parent;

    public AttributeImpl(final Element parent, final Name name, final String value) {
        this.parent = parent;
        this.name = name;
        this.value = value;
    }

    @Override
    public Name getName() {
        return name;
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
        return parent.getPath() + "/@" + name.getLocalName();
    }

}
