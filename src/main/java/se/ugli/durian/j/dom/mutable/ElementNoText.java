package se.ugli.durian.j.dom.mutable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.collections.ObservableList;
import se.ugli.durian.j.dom.collections.ObservableSet;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class ElementNoText extends AbstractMutableElement {

    private final Set<Attribute> attributes = new ObservableSet<Attribute>(this);
    private final List<Element> elements = new ObservableList<Element>(this);

    public ElementNoText(final String name, final String uri, final NodeFactory nodeFactory) {
        super(name, uri, nodeFactory);
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
    public <T extends Text> List<T> getTexts() {
        return Collections.emptyList();
    }

}
