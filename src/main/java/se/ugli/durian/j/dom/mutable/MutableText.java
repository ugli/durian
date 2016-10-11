package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.dom.utils.Id;

public class MutableText implements Text, MutableNode {

    private Element parent;
    private final String value;
    private final String id = Id.create();

    @Override
    public String id() {
        return id;
    }

    public MutableText(final String value) {
        this.value = value;
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
    public String toString() {
        return "MutableText [value=" + value + "]";
    }

}
