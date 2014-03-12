package se.ugli.durian.j.dom.mutable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class ElementSimpleText extends AbstractElement implements ListObserver<Text> {

    private final List<Text> texts = new ObservableList2<Text>(this);
    private final NodeFactory nodeFactory;

    public ElementSimpleText(final String name, final String uri, final NodeFactory nodeFactory) {
        super(name, uri, nodeFactory);
        this.nodeFactory = nodeFactory;
    }

    @Override
    public List<MutableElement> getElements() {
        return Collections.emptyList();
    }

    @Override
    public Set<Attribute> getAttributes() {
        return Collections.emptySet();
    }

    @Override
    public List<Text> getTexts() {
        return texts;
    }

    public String getText() {
        return texts.isEmpty() ? null : texts.get(0).getValue();
    }

    public void setText(final String text) {
        if (!texts.isEmpty()) {
            texts.clear();
        }
        texts.add(nodeFactory.createText(this, text));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<Content> getContent() {
        return (List) texts;
    }

    @Override
    public void add(final ObservableList2<Text> list, final Text e) {
        if (list.size() == 1) {
            throw new IllegalStateException("This is a Simple Text Element, just one text node is allowed");
        }
    }

    @Override
    public void remove(final ObservableList2<Text> list, final Text e) {
    }

}
