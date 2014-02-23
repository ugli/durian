package se.ugli.durian.j.dom.mutable;

import java.util.Collections;
import java.util.List;

import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Text;

public class MutableText implements Text {

    private final static List<Content> EMPTY_CONTENT = Collections.emptyList();

    private final String value;
    private final Element parent;

    public MutableText(final Element parent, final String value) {
        this.parent = parent;
        this.value = value;
    }

    @Override
    public List<Content> getContent() {
        return EMPTY_CONTENT;
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
        return parent.getPath() + "/text()";
    }

}
