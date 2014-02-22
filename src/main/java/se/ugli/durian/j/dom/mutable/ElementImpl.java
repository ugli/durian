package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Name;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.Text;

public class ElementImpl implements Element {

    private final Name name;
    private final Document document;
    private final Element parent;
    private final List<Attribute> attributes = new ArrayList<Attribute>();
    private final List<Content> content = new ArrayList<Content>();
    private final List<Element> elements = new ArrayList<Element>();
    private final List<Text> texts = new ArrayList<Text>();

    public ElementImpl(final Document document, final Element parent, final Name name) {
        this.document = document;
        this.parent = parent;
        this.name = name;
    }

    @Override
    public Iterable<Content> getContent() {
        return content;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public Element getParent() {
        return parent;
    }

    @Override
    public Iterable<Attribute> getAttributes() {
        return Collections.unmodifiableCollection(attributes);
    }

    @Override
    public boolean isSimpleTextNode() {
        return texts.size() == 1 && content.size() == 1;
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public void add(final Node node) {
        if (node instanceof Attribute) {
            attributes.add((Attribute) node);
        }
        else if (node instanceof Element) {
            elements.add((Element) node);
            content.add((Content) node);
        }
        else if (node instanceof Text) {
            texts.add((Text) node);
            content.add((Content) node);
        }
        else {
            throw new IllegalStateException(node.getClass().getName());
        }
    }

    @Override
    public void remove(final Node node) {
        if (node instanceof Attribute) {
            attributes.remove(node);
        }
        else if (node instanceof Element) {
            elements.remove(node);
            content.remove(node);
        }
        else if (node instanceof Text) {
            texts.remove(node);
            content.remove(node);
        }
        else {
            throw new IllegalStateException(node.getClass().getName());
        }
    }

    @Override
    public Iterable<Element> getElements() {
        return Collections.unmodifiableCollection(elements);
    }

    @Override
    public Iterable<Text> getTexts() {
        return Collections.unmodifiableCollection(texts);
    }

    @Override
    public String getText() {
        if (texts.isEmpty()) {
            return null;
        }
        if (isSimpleTextNode()) {
            return texts.get(0).getValue();
        }
        final StringBuilder textBuilder = new StringBuilder();
        for (final Iterator<Text> i = texts.iterator(); i.hasNext();) {
            textBuilder.append(i.next().getValue());
            if (i.hasNext()) {
                textBuilder.append("\n");
            }
        }
        return textBuilder.toString();
    }

    @Override
    public String getPath() {
        final String elementPath = "/" + name.getLocalName();
        if (isRoot()) {
            return elementPath;
        }
        return parent.getPath() + elementPath;
    }

}
