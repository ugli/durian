package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Name;
import se.ugli.durian.j.dom.node.Text;

public class ElementImpl implements Element {

    private final List<Attribute> attributes = new ArrayList<Attribute>();
    private final List<Content> content = new ArrayList<Content>();
    private final Document document;
    private final Name name;
    private final Element parent;

    public ElementImpl(final Document document, final Element parent, final Name name) {
        this.document = document;
        this.parent = parent;
        this.name = name;
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
    public Iterator<Element> getElements() {
        final List<Element> list = new ArrayList<Element>();
        for (final Content content : this.content) {
            if (content instanceof Element) {
                list.add((Element) content);
            }
        }
        return list.iterator();
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
        final List<Text> texts = getTextList();
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
    public Iterator<Text> getTexts() {
        return getTextList().iterator();
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public boolean isSimpleTextNode() {
        return getTextList().size() == 1 && content.size() == 1;
    }

    private List<Text> getTextList() {
        final List<Text> list = new ArrayList<Text>();
        for (final Content content : this.content) {
            if (content instanceof Text) {
                list.add((Text) content);
            }
        }
        return list;
    }

}
