package se.ugli.durian.j.dom.mutable;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class ElementWithAttributes extends AbstractElement {

    private final Set<Attribute> attributes = new LinkedHashSet<Attribute>();

    public ElementWithAttributes(final String name, final String uri, final NodeFactory nodeFactory) {
        super(name, uri, nodeFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Attribute> Set<T> getAttributes() {
        return (Set<T>) attributes;
    }

    @Override
    public List<Content> getContent() {
        return Collections.emptyList();
    }

    @Override
    public <T extends Element> List<T> getElements() {
        return Collections.emptyList();
    }

    @Override
    public <T extends Text> List<T> getTexts() {
        return Collections.emptyList();
    }

}
