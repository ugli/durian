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
import se.ugli.durian.j.dom.utils.ElementCloneCommand;

public class MutableElement implements Element, MutableNode {

    private final Set<Attribute> attributes = new LinkedHashSet<Attribute>();
    private final List<Content> content = new ArrayList<Content>();
    private String name;
    private final NodeFactory nodeFactory;
    private String uri;
    private Element parent;

    public MutableElement(final String name, final String uri, final NodeFactory nodeFactory) {
        this.name = name;
        this.uri = uri;
        this.nodeFactory = nodeFactory;
    }

    public void add(final Node node) {
        if (node instanceof Content)
            content.add((Content) node);
        else if (node instanceof Attribute)
            attributes.add((Attribute) node);
        ((MutableNode) node).setParent(this);
    }

    public void remove(final Node c) {
        content.remove(c);
        attributes.remove(c);
        ((MutableNode) c).setParent(null);
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

    @SuppressWarnings("unchecked")
    public <T extends Attribute> T addAttribute(final String name, final String uri, final String value, final NodeFactory nodeFactory) {
        final MutableAttribute attribute = nodeFactory.createAttribute(name, uri, this, value);
        add(attribute);
        return (T) attribute;
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

    @SuppressWarnings("unchecked")
    public <T extends Element> T addElement(final String name, final String uri, final NodeFactory nodeFactory) {
        final MutableElement element = nodeFactory.createElement(name, uri, this);
        add(element);
        return (T) element;
    }

    public <T extends Text> T addText(final String value) {
        return addText(value, nodeFactory);
    }

    @SuppressWarnings("unchecked")
    public <T extends Text> T addText(final String value, final NodeFactory nodeFactory) {
        final MutableText text = nodeFactory.createText(this, value);
        add(text);
        return (T) text;
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

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Attribute> T getAttributeByName(final String attributeName) {
        for (final Attribute attribute : attributes)
            if (attribute.getName().equals(attributeName))
                return (T) attribute;
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Attribute> Iterable<T> getAttributes() {
        return (Set<T>) Collections.unmodifiableSet(attributes);
    }

    @Override
    public String getAttributeValue(final String attributeName) {
        final Attribute attribute = getAttributeByName(attributeName);
        if (attribute != null)
            return attribute.getValue();
        return null;
    }

    @Override
    public Iterable<Content> getContent() {
        return content;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Element> T getElementByName(final String elementName) {
        final List<Element> elementsByName = getElementsByName(elementName);
        if (elementsByName.isEmpty())
            return null;
        else if (elementsByName.size() == 1)
            return (T) elementsByName.get(0);
        throw new IllegalStateException("There is more than one element with name: " + elementName);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <T extends Element> Iterable<T> getElements() {
        final List result = new ArrayList<Element>();
        for (final Content c : content)
            if (c instanceof Element)
                result.add(c);
        return Collections.unmodifiableList(result);
    }

    @Override
    public <T extends Element> List<T> getElementsByName(final String elementName) {
        final List<T> result = new ArrayList<T>();
        for (final T element : this.<T> getElements())
            if (element.getName().equals(elementName))
                result.add(element);
        return Collections.unmodifiableList(result);
    }

    @Override
    public String getElementText(final String elementName) {
        final Element element = getElementByName(elementName);
        if (element != null && element.getTexts().iterator().hasNext()) {
            final StringBuilder textBuilder = new StringBuilder();
            for (final Text text : element.getTexts())
                textBuilder.append(text.getValue());
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
        if (parent == null)
            return elementPath;
        return parent.getPath() + elementPath;
    }

    @Override
    public String getPath(final String childPath) {
        if (childPath.startsWith("/"))
            return getPath() + childPath;
        return getPath() + "/" + childPath;
    }

    @Override
    public String getRelativePath(final String childPath) {
        if (childPath.startsWith("/"))
            return name + childPath;
        return name + "/" + childPath;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <T extends Text> Iterable<T> getTexts() {
        final List result = new ArrayList<Text>();
        for (final Content c : content)
            if (c instanceof Text)
                result.add(c);
        return Collections.unmodifiableList(result);
    }

    @Override
    public String getUri() {
        return uri;
    }

    public int removeByQuery(final String query) {
        final List<Node> nodes = selectNodes(query);
        content.removeAll(nodes);
        attributes.removeAll(nodes);
        return nodes.size();
    }

    public boolean removeElementByName(final String elementName) {
        final Element elementByName = getElementByName(elementName);
        if (elementByName != null)
            return content.remove(elementByName);
        return false;
    }

    public int removeElementsByName(final String elementName) {
        final List<Element> elementsByName = getElementsByName(elementName);
        content.removeAll(elementsByName);
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
    public <T extends Attribute> T selectAttributeClone(final String query, final NodeFactory nodeFactory, final String attributeName) {
        return QueryManager.selectNodeClone(this, query, nodeFactory, attributeName);
    }

    @Override
    public <T extends Attribute> List<T> selectAttributes(final String query) {
        return QueryManager.selectNodes(this, query);
    }

    @Override
    public String selectAttributeValue(final String query) {
        final Attribute attribute = QueryManager.selectNode(this, query);
        if (attribute != null)
            return attribute.getValue();
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
    public <T extends Element> T selectElementClone(final String query, final NodeFactory nodeFactory, final String elementName) {
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
        if (attribute != null)
            setAttributeValue(attribute, value);
        else if (value != null)
            attributes.add(nodeFactory.createAttribute(attributeName, uri, this, value));
    }

    public boolean setAttributeValueByQuery(final String query, final String value) {
        final Attribute attribute = selectAttribute(query);
        if (attribute != null)
            setAttributeValue(attribute, value);
        return false;
    }

    private static void setAttributeValue(final Attribute attribute, final String value) {
        if (value != null && attribute instanceof MutableAttribute) {
            final MutableAttribute mutableAttribute = (MutableAttribute) attribute;
            mutableAttribute.setValue(value);
        }
        else if (value != null && !(attribute instanceof MutableAttribute))
            throw new IllegalStateException("Attribute: " + attribute + " isn't mutable.");
        else {
            final MutableElement parent = attribute.getParent();
            parent.attributes.remove(attribute);
        }
    }

    public void setElementByName(final String elementName, final Element element) {
        if (element != null && !element.getName().equals(elementName))
            throw new IllegalStateException(elementName + "!=" + element.getName());
        removeElementByName(elementName);
        if (element != null)
            content.add(element);
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

    @Deprecated
    public void sortElements(final Map<String, Integer> elementNameSortMap) {
        Collections.sort(content, new Comparator<Content>() {

            @Override
            public int compare(final Content c1, final Content c2) {
                if (c1 instanceof Text || c2 instanceof Text)
                    return -1;
                final Element o1 = (Element) c1;
                final Element o2 = (Element) c2;
                final String name1 = o1.getName();
                final String name2 = o2.getName();
                Integer v1 = elementNameSortMap.get(name1);
                if (v1 == null)
                    v1 = Integer.MAX_VALUE;
                Integer v2 = elementNameSortMap.get(name2);
                if (v2 == null)
                    v2 = Integer.MAX_VALUE;
                return v1.compareTo(v2);
            }
        });
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", uri=" + uri + "]";
    }

}
