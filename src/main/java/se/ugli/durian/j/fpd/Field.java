package se.ugli.durian.j.fpd;

import se.ugli.durian.j.dom.mutable.MutableAttribute;
import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;

class Field implements Definition {

    private final String name;
    private final int length;
    private final String targetNamespace;
    private final boolean includeEmptyValues;

    Field(final Element element, final String targetNamespace, final boolean includeEmptyValues) {
        name = element.attributeValue("name").get();
        length = Integer.parseInt(element.attributeValue("length").get());
        this.targetNamespace = targetNamespace;
        this.includeEmptyValues = includeEmptyValues;
    }

    @Override
    public Node createNode(final String data) {
        final String value = data.trim();
        if (!value.isEmpty() || includeEmptyValues)
            return new MutableAttribute(name, targetNamespace, value, new MutableNodeFactory());
        return null;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int numOfChars() {
        return length;
    }

    @Override
    public String toString() {
        return "Field [name=" + name + ", length=" + length + ", targetNamespace=" + targetNamespace + ", includeEmptyValues="
                + includeEmptyValues + "]";
    }

}
