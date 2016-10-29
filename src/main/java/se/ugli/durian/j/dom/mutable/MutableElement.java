package se.ugli.durian.j.dom.mutable;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static se.ugli.durian.j.dom.node.PrefixMapping.prefixMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.ElementCloneApi;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.NodeListener;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.dom.serialize.Serializer;
import se.ugli.durian.j.dom.utils.Id;

public class MutableElement implements Element, MutableNode {

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

    private final Set<Attribute> attributes = new LinkedHashSet<>();
    private final List<Content> content = new ArrayList<>();
    private final List<NodeListener> nodeListeners = new ArrayList<>();
    private final Map<String, String> prefixByUri = new LinkedHashMap<>();

    private final String id = Id.create();
    private String name;
    private final NodeFactory nodeFactory;
    private Optional<String> uri;

    private Optional<Element> parent = Optional.empty();

    public MutableElement(final String name, final String uri, final NodeFactory nodeFactory,
            final Iterable<PrefixMapping> prefixMappings) {
        this.name = name;
        this.uri = Optional.ofNullable(uri);
        this.nodeFactory = nodeFactory;
        if (prefixMappings != null)
            for (final PrefixMapping prefixmapping : prefixMappings)
                prefixByUri.put(prefixmapping.uri, prefixmapping.prefix.orElse(null));
        nodeListeners.add(new SetParentListener());
    }

    public Node add(final Node node) {
        if (node != null) {
            if (node instanceof Content)
                content.add((Content) node);
            else if (node instanceof Attribute)
                attributes.add(node.as(Attribute.class));
            for (final NodeListener listener : nodeListeners)
                listener.nodeAdded(node);
        }
        return node;
    }

    public void addAll(final Collection<? extends Node> nodes) {
        for (final Node node : nodes)
            add(node);
    }

    public Attribute addAttribute(final String name, final String value) {
        return addAttribute(name, uri.orElse(null), value, nodeFactory);
    }

    public Attribute addAttribute(final String name, final String uri, final String value,
            final NodeFactory nodeFactory) {
        final Attribute attribute = nodeFactory.createAttribute(name, uri, this, value);
        add(attribute);
        return attribute;
    }

    public Element addElement(final String name) {
        return addElement(name, uri.orElse(null), nodeFactory);
    }

    public Element addElement(final String name, final String uri, final NodeFactory nodeFactory,
            final PrefixMapping... prefixmappings) {
        final Element element = nodeFactory.createElement(name, uri, this, asList(prefixmappings));
        add(element);
        return element;
    }

    public void addListener(final NodeListener listener) {
        nodeListeners.add(listener);
    }

    public void addPrefixMapping(final String prefix, final String uri) {
        prefixByUri.put(uri, prefix);
    }

    public Text addText(final String value) {
        return addText(value, nodeFactory);
    }

    public Text addText(final String value, final NodeFactory nodeFactory) {
        final Text text = nodeFactory.createText(this, value);
        add(text);
        return text;
    }

    @Override
    public Optional<Attribute> attribute(final String attributeName) {
        for (final Attribute attribute : attributes)
            if (attribute.name().equals(attributeName))
                return Optional.of(attribute);
        return Optional.empty();
    }

    @Override
    public Stream<Attribute> attributes() {
        return new LinkedHashSet<>(attributes).stream();
    }

    @Override
    public Optional<String> attributeValue(final String attributeName) {
        final Optional<Attribute> attribute = attribute(attributeName);
        if (attribute.isPresent())
            return Optional.ofNullable(attribute.get().value());
        return Optional.empty();
    }

    @Override
    public ElementCloneApi clone() {
        return new MutableElementCloneApiImpl(this);
    }

    @Override
    public Stream<Content> content() {
        return new ArrayList<>(content).stream();
    }

    @Override
    public Optional<Element> element(final String elementName) {
        return elements(elementName).findFirst();
    }

    @Override
    public Stream<Element> elements() {
        return new ArrayList<>(content).stream().filter(c -> c instanceof Element).map(c -> c.as(Element.class));
    }

    @Override
    public Stream<Element> elements(final String elementName) {
        return elements().filter(e -> e.name().equals(elementName));
    }

    @Override
    public Optional<String> elementText(final String elementName) {
        final Optional<Element> element = element(elementName);
        if (element.isPresent() && element.get().hasTexts()) {
            final StringBuilder textBuilder = new StringBuilder();
            element.get().texts().forEach(text -> {
                textBuilder.append(text.value());
            });
            return Optional.of(textBuilder.toString());
        }
        return Optional.empty();
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
    public boolean hasNodes() {
        return !attributes.isEmpty() && !content.isEmpty();
    }

    @Override
    public boolean hasTexts() {
        for (final Content content : this.content)
            if (content instanceof Text)
                return true;
        return false;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public NodeFactory nodeFactory() {
        return nodeFactory;
    }

    @Override
    public Optional<Element> parent() {
        return parent;
    }

    @Override
    public String path() {
        final String selfPath = "/" + name;
        if (parent.isPresent())
            return parent.get().path() + selfPath;
        return selfPath;
    }

    @Override
    public String path(final String childPath) {
        if (childPath.startsWith("/"))
            return path() + childPath;
        return path() + "/" + childPath;
    }

    private Optional<String> prefix(final String uri, final Element element) {
        for (final PrefixMapping prefixmapping : element.prefixMappings().collect(toList()))
            if (uri.equals(prefixmapping.uri))
                return prefixmapping.prefix;
        if (element.parent().isPresent())
            return prefix(uri, element.parent().get());
        return Optional.empty();
    }

    @Override
    public Stream<PrefixMapping> prefixMappings() {
        final List<PrefixMapping> prefixmappings = new ArrayList<>();
        for (final Entry<String, String> entry : prefixByUri.entrySet())
            prefixmappings.add(prefixMapping(entry.getValue(), entry.getKey()));
        return prefixmappings.stream();
    }

    @Override
    public String qName() {
        return uri.flatMap(u -> prefix(u, this)).map(p -> p + ":" + name).orElse(name);
    }

    @Override
    public String relativePath(final String childPath) {
        if (childPath.startsWith("/"))
            return name + childPath;
        return name + "/" + childPath;
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

    public int removeAll(final Class<? extends Node> type) {
        final AtomicInteger result = new AtomicInteger(0);
        if (Attribute.class.isAssignableFrom(type))
            attributes().forEach(attribute -> {
                if (type.isInstance(attribute))
                    if (remove(attribute))
                        result.incrementAndGet();
            });
        if (Content.class.isAssignableFrom(type))
            content().forEach(content -> {
                if (type.isInstance(content))
                    if (remove(content))
                        result.incrementAndGet();
            });
        return result.get();
    }

    public int removeAll(final Stream<? extends Node> nodes) {
        final AtomicInteger result = new AtomicInteger(0);
        nodes.forEach(node -> {
            if (remove(node))
                result.incrementAndGet();
        });
        return result.get();
    }

    public boolean removeElement(final String elementName) {
        final Optional<Element> element = element(elementName);
        if (element.isPresent())
            return remove(element.get());
        return false;
    }

    public int removeElements(final String elementName) {
        return removeAll(elements(elementName));
    }

    public void removeListener(final NodeListener listener) {
        nodeListeners.add(listener);
    }

    @Override
    public MutableQueryApi select() {
        return new MutableQueryApiImpl(this);
    }

    public void setAttributeValue(final String attributeName, final String value) {
        final Optional<MutableAttribute> attributeOpt = attribute(attributeName).map(a -> a.as(MutableAttribute.class));
        if (attributeOpt.isPresent()) {
            final MutableAttribute attribute = attributeOpt.get();
            if (value == null)
                remove(attribute);
            else
                attribute.setValue(value);
        }
        else if (value != null)
            add(nodeFactory.createAttribute(attributeName, uri.orElse(null), this, value));
    }

    public void setElement(final String elementName, final Element element) {
        if (element != null && !element.name().equals(elementName))
            throw new IllegalStateException(elementName + "!=" + element.name());
        removeElement(elementName);
        if (element != null)
            add(element);
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setParent(final Element parent) {
        this.parent = Optional.ofNullable(parent);
    }

    public void setText(final String text) {
        removeAll(Text.class);
        addText(text);
    }

    public void setUri(final String uri) {
        this.uri = Optional.ofNullable(uri);
    }

    @Override
    public Stream<Text> texts() {
        return new ArrayList<>(content).stream().filter(c -> c instanceof Text).map(c -> c.as(Text.class));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", uri=" + uri + "]";
    }

    @Override
    public String toXml() {
        return Serializer.serialize(this);
    }

    @Override
    public Optional<String> uri() {
        return uri;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Element)
            return id.equals(((Element) obj).id());
        return false;
    }

}
