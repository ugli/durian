package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class MutableText implements Text, MutableNode {

    private Element parent;
    private final String value;
    private NodeFactory nodeFactory;

    public MutableText(final String value, final NodeFactory nodeFactory) {
        this.value = value;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Element> T getParent() {
        return (T) parent;
    }

    @Override
    public String getPath() {
        return parent.getPath() + "/text()";
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setParent(final Element parent) {
        this.parent = parent;
    }

    @Override
    public void setNodeFactory(final NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    @Override
    public String toString() {
        return "MutableText [value=" + value + "]";
    }

}
