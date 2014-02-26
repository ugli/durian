package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.ugli.durian.j.dom.collections.ListSynchronizer;
import se.ugli.durian.j.dom.collections.ObservableList;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Name;
import se.ugli.durian.j.dom.node.Text;

public class MutableElement implements Element {

    private final List<Attribute> attributes = new ArrayList<Attribute>();
    private final List<Content> content = new ObservableList<Content>();
    private final List<Element> elements = new ObservableList<Element>();
    private final List<Text> texts = new ObservableList<Text>();
    private final Document document;
    private final Name name;
    private final Element parent;

    public MutableElement(final Document document, final Element parent, final Name name) {
        this.document = document;
        this.parent = parent;
        this.name = name;
        ListSynchronizer.applyLiveUpdates(elements, content);
        ListSynchronizer.applyLiveUpdates(texts, content);
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public List<Content> getContent() {
        return content;
    }

    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public List<Element> getElements() {
        return elements;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Element getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        final String elementPath = "/" + name.getLocalName();
        if (isRoot()) {
            return elementPath;
        }
        return parent.getPath() + elementPath;
    }

    @Override
    public String getText() {
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
    public List<Text> getTexts() {
        return texts;
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public boolean isSimpleTextNode() {
        return texts.size() == 1 && content.size() == 1;
    }

    @Override
    public String getQName() {
        final String prefix = document.getPrefix(name.getUri());
        if (prefix != null) {
            return prefix + ":" + name.getLocalName();
        }
        return name.getLocalName();
    }
}
