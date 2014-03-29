package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.dom.query.QueryManager;

public abstract class AbstractElement implements MutableElement {

    private final String name;
    private final NodeFactory nodeFactory;
    private Element parent;
    private final String uri;

    public AbstractElement(final String name, final String uri, final NodeFactory nodeFactory) {
        this.name = name;
        this.uri = uri;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public <T extends Attribute> T addAttribute(final String name, final String value) {
        return addAttribute(name, uri, value, nodeFactory);
    }

    @Override
    public <T extends Attribute> T addAttribute(final String name, final String value, final NodeFactory nodeFactory) {
        return addAttribute(name, uri, value, nodeFactory);
    }

    @Override
    public <T extends Attribute> T addAttribute(final String name, final String uri, final String value) {
        return addAttribute(name, uri, value, nodeFactory);
    }

    @Override
    public <T extends Attribute> T addAttribute(final String name, final String uri, final String value,
            final NodeFactory nodeFactory) {
        final T attribute = nodeFactory.createAttribute(name, uri, this, value);
        getAttributes().add(attribute);
        return attribute;
    }

    @Override
    public <T extends Element> T addElement(final String name) {
        return addElement(name, uri, nodeFactory);
    }

    @Override
    public <T extends Element> T addElement(final String name, final NodeFactory nodeFactory) {
        return addElement(name, uri, nodeFactory);
    }

    @Override
    public <T extends Element> T addElement(final String name, final String uri) {
        return addElement(name, uri, nodeFactory);
    }

    @Override
    public <T extends Element> T addElement(final String name, final String uri, final NodeFactory nodeFactory) {
        final T element = nodeFactory.createElement(name, uri, this);
        getElements().add(element);
        return element;
    }

    @Override
    public <T extends Text> T addText(final String value) {
        return addText(value, nodeFactory);
    }

    @Override
    public <T extends Text> T addText(final String value, final NodeFactory nodeFactory) {
        final T text = nodeFactory.createText(this, value);
        getTexts().add(text);
        return text;
    }

    @Override
    public <T extends Element> T cloneElement() {
        return cloneElement(name, nodeFactory);
    }

    @Override
    public <T extends Element> T cloneElement(final NodeFactory nodeFactory) {
        return cloneElement(name, nodeFactory);
    }

    @Override
    public <T extends Element> T cloneElement(final String elementName) {
        return cloneElement(elementName, nodeFactory);
    }

    @Override
    public <T extends Element> T cloneElement(final String elementName, final NodeFactory nodeFactory) {
        final T element = nodeFactory.createElement(elementName, uri, null);
        for (final Attribute attribute : getAttributes()) {
            element.getAttributes()
                    .add(nodeFactory.createAttribute(attribute.getName(), attribute.getUri(), element,
                            attribute.getValue()));
        }
        for (final Content content : getContent()) {
            if (content instanceof Text) {
                final Text text = (Text) content;
                element.getTexts().add(nodeFactory.createText(element, text.getValue()));
            }
            else if (content instanceof MutableElement) {
                final MutableElement child = (MutableElement) content;
                element.getElements().add(child.cloneElement());
            }
        }
        return element;
    }

    @Override
    public <T extends Attribute> T getAttribute(final String attributeName) {
        final Set<T> attributes = getAttributes();
        for (final T attribute : attributes) {
            if (attribute.getName().equals(attributeName)) {
                return attribute;
            }
        }
        return null;
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
    public <T extends Element> T getElement(final String elementName) {
        final List<T> elements = getElements();
        for (final T element : elements) {
            if (element.getName().equals(elementName)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public <T extends Element> List<T> getElements(final String elementName) {
        final List<T> result = new ArrayList<T>();
        final List<T> elements = getElements();
        for (final T element : elements) {
            if (element.getName().equals(elementName)) {
                result.add(element);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public String getElementText(final String elementName) {
        final MutableElement element = getElement(elementName);
        if (element != null && !element.getTexts().isEmpty()) {
            return element.getTexts().get(0).getValue();
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Element> T getParent() {
        return (T) parent;
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
    public String getUri() {
        return uri;
    }

    @Override
    public Set<String> getUriSet() {
        final Set<String> result = new LinkedHashSet<String>();
        if (uri != null) {
            result.add(uri);
        }
        for (final Element element : getElements()) {
            result.addAll(element.getUriSet());
        }
        return result;
    }

    @Override
    public boolean isSimpleTextNode() {
        return getTexts().size() == 1 && getContent().size() == 1;
    }

    @Override
    public <T extends Attribute> T selectAttribute(final String path) {
        return QueryManager.selectNode(this, path);
    }

    @Override
    public <T extends Attribute> List<T> selectAttributes(final String path) {
        return QueryManager.selectNodes(this, path);
    }

    @Override
    public <T extends Element> T selectElement(final String path) {
        return QueryManager.selectNode(this, path);
    }

    @Override
    public <T extends Element> List<T> selectElements(final String path) {
        return QueryManager.selectNodes(this, path);
    }

    @Override
    public <T extends Node> T selectNode(final String path) {
        return QueryManager.selectNode(this, path);
    }

    @Override
    public <T extends Node> List<T> selectNodes(final String path) {
        return QueryManager.selectNodes(this, path);
    }

    @Override
    public String selectText(final String path) {
        final Text text = QueryManager.selectNode(this, path);
        if (text != null) {
            return text.getValue();
        }
        return null;
    }

    @Override
    public List<String> selectTexts(final String path) {
        final List<Text> texts = QueryManager.selectNodes(this, path);
        final List<String> strings = new ArrayList<String>();
        for (final Text text : texts) {
            strings.add(text.getValue());
        }
        return strings;
    }

    @Override
    public void setAttributeValue(final String attributeName, final String value) {
        final Attribute attribute = getAttribute(attributeName);
        if (attribute != null) {
            if (value != null) {
                attribute.setValue(value);
            }
            else {
                getAttributes().remove(attribute);
            }
        }
        else if (value != null) {
            getAttributes().add(nodeFactory.createAttribute(attributeName, uri, this, value));
        }
    }

    public void setElement(final MutableElement element, final String elementName) {
        final MutableElement oldElement = getElement(elementName);
        if (oldElement != null) {
            getElements().remove(oldElement);
        }
        if (element != null) {
            getElements().add(element);
        }
    }

    @Override
    public void setParent(final MutableElement parent) {
        this.parent = parent;
    }

    public void sortElements(final Map<String, Integer> elementNameSortMap) {
        Collections.sort(getElements(), new Comparator<Element>() {

            @Override
            public int compare(final Element o1, final Element o2) {
                final String name1 = o1.getName();
                final String name2 = o2.getName();
                Integer v1 = elementNameSortMap.get(name1);
                if (v1 == null) {
                    v1 = Integer.MAX_VALUE;
                }
                Integer v2 = elementNameSortMap.get(name2);
                if (v2 == null) {
                    v2 = Integer.MAX_VALUE;
                }
                return v1.compareTo(v2);
            }
        });
    }

}
