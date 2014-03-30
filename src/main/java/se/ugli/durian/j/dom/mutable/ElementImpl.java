package se.ugli.durian.j.dom.mutable;

import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.collections.ListSynchronizer;
import se.ugli.durian.j.dom.collections.ObservableList;
import se.ugli.durian.j.dom.collections.ObservableSet;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class ElementImpl extends AbstractMutableElement {

    private final Set<Attribute> attributes = new ObservableSet<Attribute>(this);
    private final ObservableList<Content> content = new ObservableList<Content>(this);
    private final ObservableList<Element> elements = new ObservableList<Element>(this);
    private final ObservableList<Text> texts = new ObservableList<Text>(this);

    public ElementImpl(final String name, final String uri, final NodeFactory nodeFactory) {
        super(name, uri, nodeFactory);
        ListSynchronizer.applyLiveUpdates(elements, content);
        ListSynchronizer.applyLiveUpdates(texts, content);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Attribute> Set<T> getAttributes() {
        return (Set<T>) attributes;
    }

    @Override
    public List<Content> getContent() {
        return content;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Element> List<T> getElements() {
        return (List<T>) elements;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Text> List<T> getTexts() {
        return (List<T>) texts;
    }

}