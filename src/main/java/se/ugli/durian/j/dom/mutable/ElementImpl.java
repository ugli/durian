package se.ugli.durian.j.dom.mutable;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class ElementImpl extends AbstractElement {

    private final Set<Attribute> attributes = new LinkedHashSet<Attribute>();
    private final List<Content> content = new ObservableList<Content>();
    private final List<MutableElement> elements = new ObservableList<MutableElement>();
    private final List<Text> texts = new ObservableList<Text>();

    public ElementImpl(final String name, final String uri, final NodeFactory nodeFactory) {
        super(name, uri, nodeFactory);
        ListSynchronizer.applyLiveUpdates(elements, content, this);
        ListSynchronizer.applyLiveUpdates(texts, content, this);
    }

    @Override
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public List<Content> getContent() {
        return content;
    }

    @Override
    public List<MutableElement> getElements() {
        return elements;
    }

    @Override
    public List<Text> getTexts() {
        return texts;
    }

    public void elementAdded(final ElementImpl element) {
        element.setParent(this);
    }

}
