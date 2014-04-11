package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import se.ugli.durian.j.dom.collections.ObservableCollection;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.dom.query.QueryManager;
import se.ugli.durian.j.dom.utils.ElementCloneCommand;

public abstract class AbstractMutableElement implements MutableElement {

    private String name;
    private NodeFactory nodeFactory;
    private Element parent;
    private String uri;

    public AbstractMutableElement(final String name, final String uri, final NodeFactory nodeFactory) {
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
        return ElementCloneCommand.execute(name, this, nodeFactory);
    }

    @Override
    public <T extends Element> T cloneElement(final NodeFactory nodeFactory) {
        return ElementCloneCommand.execute(name, this, nodeFactory);
    }

    @Override
    public <T extends Element> T cloneElement(final String elementName) {
        return ElementCloneCommand.execute(elementName, this, nodeFactory);
    }

    @Override
    public <T extends Element> T cloneElement(final String elementName, final NodeFactory nodeFactory) {
        return ElementCloneCommand.execute(elementName, this, nodeFactory);
    }

    @SuppressWarnings("unused")
    @Override
    public void elementAdded(final ObservableCollection<MutableNode> list, final MutableNode node) {
        node.setParent(this);
    }

    @SuppressWarnings("unused")
    @Override
    public void elementRemoved(final ObservableCollection<MutableNode> list, final Object object) {
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
        for (final T element : this.<T> getElements()) {
            if (element.getName().equals(elementName)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public <T extends Element> List<T> getElements(final String elementName) {
        final List<T> result = new ArrayList<T>();
        for (final T element : this.<T> getElements()) {
            if (element.getName().equals(elementName)) {
                result.add(element);
            }
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public String getElementText(final String elementName) {
        final MutableElement element = getElement(elementName);
        if (element != null && !element.getTexts().isEmpty()) {
            final StringBuilder textBuilder = new StringBuilder();
            for (final Text text : element.getTexts()) {
                textBuilder.append(text.getValue());
            }
            return textBuilder.toString();
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public NodeFactory getNodeFactory() {
        return nodeFactory;
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
    public <T extends Attribute> T selectAttribute(final String query) {
        return QueryManager.selectNode(this, query);
    }

    @Override
    public <T extends Attribute> T selectAttributeClone(final String query) {
        return QueryManager.selectNodeClone(this, query, nodeFactory);
    }

    @Override
    public <T extends Attribute> T selectAttributeClone(final String query, final NodeFactory nodeFactory) {
        return QueryManager.selectNodeClone(this, query, nodeFactory);
    }

    @Override
    public <T extends Attribute> T selectAttributeClone(final String query, final NodeFactory nodeFactory,
            final String attributeName) {
        return QueryManager.selectNodeClone(this, query, nodeFactory, attributeName);
    }

    @Override
    public <T extends Attribute> List<T> selectAttributes(final String query) {
        return QueryManager.selectNodes(this, query);
    }

    @Override
    public String selectAttributeValue(final String query) {
        final Attribute attribute = QueryManager.selectNode(this, query);
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    @Override
    public <T extends Element> T selectElement(final String query) {
        return QueryManager.selectNode(this, query);
    }

    @Override
    public <T extends Element> T selectElementClone(final String query) {
        return QueryManager.selectNodeClone(this, query, nodeFactory);
    }

    @Override
    public <T extends Element> T selectElementClone(final String query, final NodeFactory nodeFactory) {
        return QueryManager.selectNodeClone(this, query, nodeFactory);
    }

    @Override
    public <T extends Attribute> T selectElementClone(final String query, final NodeFactory nodeFactory,
            final String elementName) {
        return QueryManager.selectNodeClone(this, query, nodeFactory, elementName);
    }

    @Override
    public <T extends Element> List<T> selectElements(final String query) {
        return QueryManager.selectNodes(this, query);
    }

    @Override
    public <T extends Node> T selectNode(final String query) {
        return QueryManager.selectNode(this, query);
    }

    @Override
    public <T extends Node> List<T> selectNodes(final String query) {
        return QueryManager.selectNodes(this, query);
    }

    @Override
    public String selectText(final String query) {
        return QueryManager.selectText(this, query);
    }

    @Override
    public List<String> selectTexts(final String query) {
        return QueryManager.selectTexts(this, query);
    }

    @Override
    public void setAttributeValue(final String attributeName, final String value) {
        final MutableAttribute attribute = getAttribute(attributeName);
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

    @Override
    public void setElement(final Element element, final String elementName) {
        final Element oldElement = getElement(elementName);
        if (oldElement != null) {
            getElements().remove(oldElement);
        }
        if (element != null) {
            getElements().add(element);
        }
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setNodeFactory(final NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    @Override
    public void setParent(final Element parent) {
        this.parent = parent;
    }

    @Override
    public void setUri(final String uri) {
        this.uri = uri;
    }

    @Override
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", uri=" + uri + "]";
    }

}
