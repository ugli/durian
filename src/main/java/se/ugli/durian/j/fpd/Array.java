package se.ugli.durian.j.fpd;

import java.util.ArrayList;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.PrefixMapping;

class Array implements Definition {

    private final String name;
    private final int size;
    private final int elementLength;
    private final String targetNamespace;
    private final boolean includeEmptyValues;

    Array(final Element element, final String targetNamespace, final boolean includeEmptyValues) {
        name = element.attributeValue("name").get();
        size = Integer.parseInt(element.attributeValue("size").get());
        elementLength = Integer.parseInt(element.attributeValue("elementLength").get());
        this.targetNamespace = targetNamespace;
        this.includeEmptyValues = includeEmptyValues;
    }

    @Override
    public Node createNode(final String data) {
        final MutableElement element = new MutableElement(name, targetNamespace, new MutableNodeFactory(), new ArrayList<PrefixMapping>());
        int beginIndex = 0;
        for (int i = 0; i < size; i++) {
            final int endIndex = beginIndex + elementLength;
            final String value = data.substring(beginIndex, endIndex).trim();
            if (!value.isEmpty() || includeEmptyValues) {
                final MutableElement e = element.addElement("element");
                e.addAttribute("index", String.valueOf(i + 1));
                e.addAttribute("value", value);
            }
            beginIndex = endIndex;
        }
        return element;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int numOfChars() {
        return size * elementLength;
    }

    @Override
    public String toString() {
        return "Array [name=" + name + ", size=" + size + ", elementLength=" + elementLength + ", targetNamespace=" + targetNamespace
                + ", includeEmptyValues=" + includeEmptyValues + "]";
    }

}
