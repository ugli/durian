package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class MutableElement implements Element {

    private final Set<Attribute> attributes = new LinkedHashSet<Attribute>();
    private final List<Content> content = new ObservableList<Content>();
    private final List<Element> elements = new ObservableList<Element>();
    private final List<Text> texts = new ObservableList<Text>();
    private Element parent;
    private final String name;
    private final String uri;
    private final NodeFactory nodeFactory;

    public MutableElement(final String name, final String uri, final NodeFactory nodeFactory) {
        this.name = name;
        this.uri = uri;
        this.nodeFactory = nodeFactory;
        ListSynchronizer.applyLiveUpdates(elements, content, this);
        ListSynchronizer.applyLiveUpdates(texts, content, this);
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
    public List<Element> getElements() {
        return elements;
    }

    @Override
    public Element getParent() {
        return parent;
    }

    @Override
    public List<Text> getTexts() {
        return texts;
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
    public String getAttributeValue(final String attributeName) {
        final Attribute attribute = getAttribute(attributeName);
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    @Override
    public Attribute getAttribute(final String attributeName) {
        for (final Attribute attribute : attributes) {
            if (attribute.getName().equals(attributeName)) {
                return attribute;
            }
        }
        return null;
    }

    @Override
    public void setAttributeValue(final String attributeName, final String value) {
        final Attribute attribute = getAttribute(attributeName);
        if (attribute != null) {
            attribute.setValue(value);
        }
        else {
            attributes.add(nodeFactory.createAttribute(attributeName, uri, this, value));
        }
    }

    @Override
    public List<Element> getElements(final String elementName) {
        final List<Element> result = new ArrayList<Element>();
        for (final Element element : elements) {
            if (element.getName().equals(elementName)) {
                result.add(element);
            }
        }
        return Collections.unmodifiableList(result);
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> List<T> getTypedElements(final String elementName) {
        return (List<T>) getElements(elementName);
    }

    @Override
    public Element getElement(final String elementName) {
        for (final Element element : elements) {
            if (element.getName().equals(elementName)) {
                return element;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T getTypedElement(final String elementName) {
        return (T) getElement(elementName);
    }

    public void setElement(final Element element) {
        if (element != null) {
            final Element element2 = getElement(element.getName());
            if (element2 != null) {
                getElements().remove(element2);
            }
            getElements().add(element);
        }
    }

    public void elementAdded(final MutableElement element) {
        element.parent = this;
    }

    public Element clone(final String elementName) {
        final Element element = nodeFactory.createElement(elementName, uri, null);
        for (final Attribute attribute : attributes) {
            element.getAttributes()
                    .add(nodeFactory.createAttribute(attribute.getName(), attribute.getUri(), element,
                            attribute.getValue()));
        }
        for (final Content content : this.content) {
            if (content instanceof Text) {
                final Text text = (Text) content;
                element.getTexts().add(nodeFactory.createText(element, text.getValue()));
            }
            else if (content instanceof Element) {
                final Element child = (Element) content;
                element.getElements().add(child.clone());
            }
        }
        return element;
    }

    @Override
    public Element clone() {
        return clone(name);
    }

    @Override
    public void setParent(final Element parent) {
        this.parent = parent;
    }

    @Override
    public String getPath() {
        final String elementPath = "/" + name;
        if (parent == null) {
            return elementPath;
        }
        return parent.getPath() + elementPath;
    }

    @Override
    public String getPath(final String childPath) {
        if (childPath.startsWith("/")) {
            return getPath() + childPath;
        }
        return getPath() + "/" + childPath;
    }

    @Override
    public String getRelativePath(final String childPath) {
        if (childPath.startsWith("/")) {
            return name + childPath;
        }
        return name + "/" + childPath;
    }

    @Override
    public Set<String> getUriSet() {
        final Set<String> result = new LinkedHashSet<String>();
        if (uri != null) {
            result.add(uri);
        }
        for (final Element element : elements) {
            result.addAll(element.getUriSet());
        }
        return result;
    }

}
