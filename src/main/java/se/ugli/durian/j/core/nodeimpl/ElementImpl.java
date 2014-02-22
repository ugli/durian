package se.ugli.durian.j.core.nodeimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.ugli.durian.j.core.Attribute;
import se.ugli.durian.j.core.Content;
import se.ugli.durian.j.core.Document;
import se.ugli.durian.j.core.Element;
import se.ugli.durian.j.core.Name;
import se.ugli.durian.j.core.Text;

public class ElementImpl implements Element {

    private final Name name;
    private final Document document;
    private final Element parent;
    private final List<Attribute> attributes;
    private final List<Content> content;
    private final List<Element> elements;
    private final List<Text> texts;

    public ElementImpl(final Name name, final Document document, final Element parent,
	    final Collection<Attribute> attributes) {
	this.name = name;
	this.document = document;
	this.parent = parent;
	this.attributes = new ArrayList<Attribute>(attributes);
	this.content = new ArrayList<Content>();
	this.elements = new ArrayList<Element>();
	this.texts = new ArrayList<Text>();
    }

    @Override
    public Collection<Content> getContent() {
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
    public Collection<Attribute> getAttributes() {
	return attributes;
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
    public void append(final Content content) {
	this.content.add(content);
	if (content instanceof Element)
	    elements.add((Element) content);
	else if (content instanceof Text)
	    texts.add((Text) content);
	else
	    throw new IllegalStateException(content.getClass().getName());
    }
}
