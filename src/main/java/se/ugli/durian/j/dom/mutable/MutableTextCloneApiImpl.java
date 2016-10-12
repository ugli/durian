package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.dom.node.TextCloneApi;

public class MutableTextCloneApiImpl implements TextCloneApi {

    private final String value;
    private final NodeFactory nodeFactory;
    private final Element parent;

    public MutableTextCloneApiImpl(final MutableText mutableText) {
        this.parent = null;
        this.value = mutableText.getValue();
        this.nodeFactory = mutableText.nodeFactory();
    }

    @Override
    public <T extends Text> T text() {
        return nodeFactory.createText(parent, value);
    }

    @Override
    public <T extends Text> T text(final Element parent) {
        return nodeFactory.createText(parent, value);
    }

    @Override
    public <T extends Text> T text(final NodeFactory nodeFactory) {
        return nodeFactory.createText(parent, value);
    }

    @Override
    public <T extends Text> T text(final Element parent, final NodeFactory nodeFactory) {
        return nodeFactory.createText(parent, value);
    }

}
