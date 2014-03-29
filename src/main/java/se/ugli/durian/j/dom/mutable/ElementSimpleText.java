package se.ugli.durian.j.dom.mutable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class ElementSimpleText extends AbstractMutableElement implements ListObserver {

    private final List<Text> texts = new ObservableList2<Text>(this);

    public ElementSimpleText(final String name, final String uri, final NodeFactory nodeFactory) {
        super(name, uri, nodeFactory);
    }

    @SuppressWarnings("unused")
    @Override
    public void add(final ObservableList2<?> list, final Object obj) {
        if (list.size() == 1) {
            throw new IllegalStateException("This is a Simple Text Element, just one text node is allowed");
        }
    }

    @Override
    public <T extends Attribute> Set<T> getAttributes() {
        return Collections.emptySet();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<Content> getContent() {
        return (List) texts;
    }

    @Override
    public <T extends Element> List<T> getElements() {
        return Collections.emptyList();
    }

    public String getText() {
        return texts.isEmpty() ? null : texts.get(0).getValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Text> List<T> getTexts() {
        return (List<T>) texts;
    }

    @SuppressWarnings("unused")
    @Override
    public void remove(final ObservableList2<?> list, final Object obj) {
    }

    public void setText(final String text) {
        if (!texts.isEmpty()) {
            texts.clear();
        }
        texts.add(getNodeFactory().createText(this, text));
    }

}
