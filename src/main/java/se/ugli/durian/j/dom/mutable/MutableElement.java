package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.NodeListener;
import se.ugli.durian.j.dom.node.Prefixmapping;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.dom.query.QueryManager;
import se.ugli.durian.j.dom.serialize.Serializer;
import se.ugli.durian.j.dom.utils.ElementCloneCommand;
import se.ugli.durian.j.dom.utils.Id;

public class MutableElement implements Element, MutableNode {

    private final Set<Attribute> attributes = new LinkedHashSet<Attribute>();
    private final List<Content> content = new ArrayList<Content>();
    private String name;
    private final NodeFactory nodeFactory;
    private String uri;
    private Element parent;
    private final List<NodeListener> nodeListeners = new ArrayList<NodeListener>();
    private final Iterable<Prefixmapping> prefixmappings;
    private final String id = Id.create();

    @Override
    public String id() {
        return id;
    }

    public MutableElement(final String name, final String uri, final NodeFactory nodeFactory,
            final Iterable<Prefixmapping> prefixmappings) {
        this.name = name;
        this.uri = uri;
        this.nodeFactory = nodeFactory;
        this.prefixmappings = prefixmappings;
        nodeListeners.add(new SetParentListener());
    }

    private class SetParentListener implements NodeListener {

        @Override
        public void nodeAdded(final Node node) {
            ((MutableNode) node).setParent(MutableElement.this);
        }

        @Override
        public void nodeRemoved(final Node node) {
            ((MutableNode) node).setParent(null);
        }

    }

    public void addListener(final NodeListener listener) {
        nodeListeners.add(listener);
    }

    public void removeListener(final NodeListener listener) {
        nodeListeners.add(listener);
    }

    public Node add(final Node node) {
        if (node != null) {
            if (node instanceof Content)
                content.add((Content) node);
            else if (node instanceof Attribute)
                attributes.add((Attribute) node);
            for (final NodeListener listener : nodeListeners)
                listener.nodeAdded(node);
        }
        return node;
    }

    public void addAll(final Collection<? extends Node> nodes) {
        for (final Node node : nodes)
            add(node);
    }

    public boolean remove(final Node node) {
        boolean result = false;
        if (node != null) {
            if (node instanceof Content)
                result = content.remove(node);
            if (node instanceof Attribute)
                result = attributes.remove(node);
            for (final NodeListener listener : nodeListeners)
                listener.nodeAdded(node);
        }
        return result;
    }

    public int removeAll(final Collection<? extends Node> nodes) {
        int result = 0;
        for (final Node node : nodes)
            if (remove(node))
                result++;
        return result;
    }

    public int removeAll(final Class<? extends Node> type) {
        int result = 0;
        if (Attribute.class.isAssignableFrom(type))
            for (final Attribute attribute : new ArrayList<Attribute>((Collection<Attribute>) getAttributes()))
                if (type.isInstance(attribute))
                    if (remove(attribute))
                        result++;
        if (Content.class.isAssignableFrom(type))
            for (final Content content : new ArrayList<Content>((Collection<Content>) getContent()))
                if (type.isInstance(content))
                    if (remove(content))
                        result++;
        return result;
    }

    public <T extends Attribute> T addAttribute(final String name, final String value) {
        return addAttribute(name, uri, value, nodeFactory);
    }

    @SuppressWarnings("unchecked")
    public <T extends Attribute> T addAttribute(final String name, final String uri, final String value, final NodeFactory nodeFactory) {
        return (T) add(nodeFactory.createAttribute(name, uri, this, value));
    }

    public <T extends Element> T addElement(final String name) {
        return addElement(name, uri, nodeFactory);
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T addElement(final String name, final String uri, final NodeFactory nodeFactory,
            final Prefixmapping... prefixmappings) {
        return (T) add(nodeFactory.createElement(name, uri, this, Arrays.asList(prefixmappings)));
    }

    public <T extends Text> T addText(final String value) {
        return addText(value, nodeFactory);
    }

    @SuppressWarnings("unchecked")
    public <T extends Text> T addText(final String value, final NodeFactory nodeFactory) {
        return (T) add(nodeFactory.createText(this, value));
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
        return (Iterable<T>) Collections.unmodifiableSet(attributes);
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
        return removeAll(selectNodes(query));
    }

    public boolean removeElementByName(final String elementName) {
        return remove(getElementByName(elementName));
    }

    public int removeElementsByName(final String elementName) {
        return removeAll(getElementsByName(elementName));
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
        final MutableAttribute attribute = getAttributeByName(attributeName);
        if (attribute != null) {
            if (value == null)
                remove(attribute);
            else
                attribute.setValue(value);
        }
        else if (value != null)
            add(nodeFactory.createAttribute(attributeName, uri, this, value));
    }

    public boolean setAttributeValueByQuery(final String query, final String value) {
        final MutableAttribute attribute = selectAttribute(query);
        if (attribute != null)
            attribute.setValue(value);
        return attribute != null;
    }

    public void setElementByName(final String elementName, final Element element) {
        if (element != null && !element.getName().equals(elementName))
            throw new IllegalStateException(elementName + "!=" + element.getName());
        removeElementByName(elementName);
        if (element != null)
            add(element);
    }

    @Override
    public boolean evaluteBoolean(final String query) {
        return QueryManager.evaluteBoolean(this, query);
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

    @Override
    public boolean hasAttributes() {
        return !attributes.isEmpty();
    }

    @Override
    public boolean hasElements() {
        for (final Content content : this.content)
            if (content instanceof Element)
                return true;
        return false;
    }

    @Override
    public boolean hasTexts() {
        for (final Content content : this.content)
            if (content instanceof Text)
                return true;
        return false;
    }

    @Override
    public boolean hasNodes() {
        return !attributes.isEmpty() && !content.isEmpty();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", uri=" + uri + "]";
    }

    @Override
    public Iterable<Prefixmapping> prefixmappings() {
        return prefixmappings;
    }

    @Override
    public String qName() {
        if (uri != null) {
            final String prefix = prefix(uri, this);
            if (prefix != null)
                return prefix + ":" + name;
        }
        return name;
    }

    private String prefix(final String uri, final Element element) {
        for (final Prefixmapping prefixmapping : element.prefixmappings())
            if (uri.equals(prefixmapping.uri))
                return prefixmapping.prefix;
        if (element.getParent() != null)
            return prefix(uri, element.getParent());
        return null;
    }

    @Override
    public String toXml() {
        return Serializer.serialize(this);
    }

}
