package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Text;

public class MutableElement implements Element {

    private final Set<Attribute> attributes = new LinkedHashSet<Attribute>();
    private final List<Content> content = new ObservableList<Content>();
    private final List<Element> elements = new ObservableList<Element>();
    private final List<Text> texts = new ObservableList<Text>();
    private Document document;
    private Element parent;
    private final String name;
    private final String uri;

    public MutableElement(final String name, final String uri, final Document document) {
        this.name = name;
        this.uri = uri;
        this.document = document;
        ListSynchronizer.applyLiveUpdates(elements, content, this);
        ListSynchronizer.applyLiveUpdates(texts, content, this);
    }

    public MutableElement(final String name, final String uri) {
        this(name, uri, null);
    }

    @Override
    public Set<Attribute> getAttributes() {
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
    public Element getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        final String elementPath = "/" + name;
        if (isRoot()) {
            return elementPath;
        }
        return parent.getPath() + elementPath;
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
    public String getName() {
        return name;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getAttributeValue(String attributeName) {
        Attribute attribute = getAttribute(attributeName);
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    @Override
    public Attribute getAttribute(String attributeName) {
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals(attributeName)) {
                return attribute;
            }
        }
        return null;
    }

    @Override
    public void setAttributeValue(String attributeName, String value) {
        Attribute attribute = getAttribute(attributeName);
        if (attribute != null) {
            attribute.setValue(value);
        }
    }

    @Override
    public List<Element> getElements(String elementName) {
        List<Element> result = new ArrayList<Element>();
        for (Element element : elements) {
            if (element.getName().equals(elementName)) {
                result.add(element);
            }
        }
        return Collections.unmodifiableList(result);
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> List<T> getTypedElements(String elementName) {
        return (List<T>) getElements(elementName);
    }

    @Override
    public Element getElement(String elementName) {
        for (Element element : elements) {
            if (element.getName().equals(elementName)) {
                return element;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T getTypedElement(String elementName) {
        return (T) getElement(elementName);
    }

    public void elementAdded(MutableElement element) {
        element.parent = this;
        element.document = document;
    }

}
