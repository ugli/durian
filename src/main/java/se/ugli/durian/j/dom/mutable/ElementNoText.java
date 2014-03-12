package se.ugli.durian.j.dom.mutable;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class ElementNoText extends AbstractElement implements ListObserver {

    private final Set<Attribute> attributes = new LinkedHashSet<Attribute>();
    private final List<MutableElement> elements = new ObservableList2<MutableElement>(this);

    public ElementNoText(final String name, final String uri, final NodeFactory nodeFactory) {
        super(name, uri, nodeFactory);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Content> getContent() {
        return (List) elements;
    }

    @Override
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public List<MutableElement> getElements() {
        return elements;
    }

    @Override
    public List<Text> getTexts() {
        return Collections.emptyList();
    }

    @Override
    public void add(final ObservableList2<?> list, final Object obj) {
        final MutableElement e = (MutableElement) obj;
        e.setParent(this);
    }

    @Override
    public void remove(final ObservableList2<?> list, final Object obj) {
    }

}
