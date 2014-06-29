package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import se.ugli.durian.j.dom.collections.CollectionObserver;
import se.ugli.durian.j.dom.collections.ListSynchronizer;
import se.ugli.durian.j.dom.collections.ObservableCollection;
import se.ugli.durian.j.dom.collections.ObservableList;
import se.ugli.durian.j.dom.collections.ObservableSet;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.dom.query.QueryManager;
import se.ugli.durian.j.dom.utils.ElementCloneCommand;

public class MutableElement implements Element, MutableNode, CollectionObserver<MutableNode> {

    private final Set<Attribute> attributes;
    private final ObservableList<Content> content;
    private final ObservableList<Element> elements;
    private final ObservableList<Text> texts;
    private String name;
    private final NodeFactory nodeFactory;
    private String uri;
    private Element parent;

    public MutableElement(final String name, final String uri, final NodeFactory nodeFactory) {
        this(name, uri, nodeFactory, new LinkedHashSet<Attribute>(), new ArrayList<Content>(),
                new ArrayList<Element>(), new ArrayList<Text>());
    }

    public MutableElement(final String name, final String uri, final NodeFactory nodeFactory,
            final Set<Attribute> attributeBackedSet, final List<Content> contentBackendList,
            final List<Element> elementBackendList, final List<Text> textBackendList) {
        this.name = name;
        this.uri = uri;
        this.nodeFactory = nodeFactory;
        attributes = new ObservableSet<Attribute>(attributeBackedSet, this);
        content = new ObservableList<Content>(contentBackendList, this);
        elements = new ObservableList<Element>(elementBackendList, this);
        texts = new ObservableList<Text>(textBackendList, this);
        ListSynchronizer.applyLiveUpdates(elements, content);
        ListSynchronizer.applyLiveUpdates(texts, content);
    }

    public <T extends Attribute> T addAttribute(final String name, final String value) {
        return addAttribute(name, uri, value, nodeFactory);
    }

    public <T extends Attribute> T addAttribute(final String name, final String value, final NodeFactory nodeFactory) {
        return addAttribute(name, uri, value, nodeFactory);
    }

    public <T extends Attribute> T addAttribute(final String name, final String uri, final String value) {
        return addAttribute(name, uri, value, nodeFactory);
    }

    public <T extends Attribute> T addAttribute(final String name, final String uri, final String value,
            final NodeFactory nodeFactory) {
        final T attribute = nodeFactory.createAttribute(name, uri, this, value);
        getAttributes().add(attribute);
        return attribute;
    }

    public <T extends Element> T addElement(final String name) {
        return addElement(name, uri, nodeFactory);
    }

    public <T extends Element> T addElement(final String name, final NodeFactory nodeFactory) {
        return addElement(name, uri, nodeFactory);
    }

    public <T extends Element> T addElement(final String name, final String uri) {
        return addElement(name, uri, nodeFactory);
    }

    public <T extends Element> T addElement(final String name, final String uri, final NodeFactory nodeFactory) {
        final T element = nodeFactory.createElement(name, uri, this);
        getElements().add(element);
        return element;
    }

    public <T extends Text> T addText(final String value) {
        return addText(value, nodeFactory);
    }

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
    public <T extends Attribute> T getAttributeByName(final String attributeName) {
        final Set<T> attributes = getAttributes();
        for (final T attribute : attributes) {
            if (attribute.getName().equals(attributeName)) {
                return attribute;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Attribute> Set<T> getAttributes() {
        return (Set<T>) attributes;
    }

    @Override
    public String getAttributeValue(final String attributeName) {
        final Attribute attribute = getAttributeByName(attributeName);
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    @Override
    public List<Content> getContent() {
        return content;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Element> T getElementByName(final String elementName) {
        final List<Element> elementsByName = getElementsByName(elementName);
        if (elementsByName.isEmpty()) {
            return null;
        }
        else if (elementsByName.size() == 1) {
            return (T) elementsByName.get(0);
        }
        throw new IllegalStateException("There is more than one element with name: " + elementName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Element> List<T> getElements() {
        return (List<T>) elements;
    }

    @Override
    public <T extends Element> List<T> getElementsByName(final String elementName) {
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
        final Element element = getElementByName(elementName);
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

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Text> List<T> getTexts() {
        return (List<T>) texts;
    }

    @Override
    public String getUri() {
        return uri;
    }

    public int removeByQuery(final String query) {
        final List<Node> nodes = selectNodes(query);
        elements.removeAll(nodes);
        texts.removeAll(nodes);
        attributes.removeAll(nodes);
        return nodes.size();
    }

    public boolean removeElementByName(final String elementName) {
        final Element elementByName = getElementByName(elementName);
        if (elementByName != null) {
            return elements.remove(elementByName);
        }
        return false;
    }

    public int removeElementsByName(final String elementName) {
        final List<Element> elementsByName = getElementsByName(elementName);
        elements.removeAll(elementsByName);
        return elementsByName.size();
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
    public <T extends Element> T selectElementClone(final String query, final NodeFactory nodeFactory,
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

    public void setAttributeValueByName(final String attributeName, final String value) {
        final Attribute attribute = getAttributeByName(attributeName);
        if (attribute != null) {
            setAttributeValue(attribute, value);
        }
        else if (value != null) {
            getAttributes().add(nodeFactory.createAttribute(attributeName, uri, this, value));
        }
    }

    public boolean setAttributeValueByQuery(final String query, final String value) {
        final Attribute attribute = selectAttribute(query);
        if (attribute != null) {
            setAttributeValue(attribute, value);
        }
        return false;
    }

    private static void setAttributeValue(final Attribute attribute, final String value) {
        if (value != null && attribute instanceof MutableAttribute) {
            final MutableAttribute mutableAttribute = (MutableAttribute) attribute;
            mutableAttribute.setValue(value);
        }
        else if (value != null && !(attribute instanceof MutableAttribute)) {
            throw new IllegalStateException("Attribute: " + attribute + " isn't mutable.");
        }
        else {
            final Element parent = attribute.getParent();
            parent.getAttributes().remove(attribute);
        }
    }

    public void setElementByName(final String elementName, final Element element) {
        if (element != null && !element.getName().equals(elementName)) {
            throw new IllegalStateException(elementName + "!=" + element.getName());
        }
        removeElementByName(elementName);
        if (element != null) {
            getElements().add(element);
        }
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setParent(final Element parent) {
        this.parent = parent;
    }

    public void setUri(final String uri) {
        this.uri = uri;
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", uri=" + uri + "]";
    }

}
