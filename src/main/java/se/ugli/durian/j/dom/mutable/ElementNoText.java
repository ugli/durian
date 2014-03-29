package se.ugli.durian.j.dom.mutable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class ElementNoText extends AbstractElement implements ListObserver {

    private final Set<Attribute> attributes = Collections.newSetFromMap(new ConcurrentHashMap<Attribute, Boolean>());
    private final List<Element> elements = new ObservableList2<Element>(this);

    public ElementNoText(final String name, final String uri, final NodeFactory nodeFactory) {
        super(name, uri, nodeFactory);
    }

    @SuppressWarnings("unused")
    @Override
    public void add(final ObservableList2<?> list, final Object obj) {
        final MutableElement e = (MutableElement) obj;
        e.setParent(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Attribute> Set<T> getAttributes() {
        return (Set<T>) attributes;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Content> getContent() {
        return (List) elements;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Element> List<T> getElements() {
        return (List<T>) elements;
    }

    @Override
    public List<Text> getTexts() {
        return Collections.emptyList();
    }

    @SuppressWarnings("unused")
    @Override
    public void remove(final ObservableList2<?> list, final Object obj) {
    }

}
