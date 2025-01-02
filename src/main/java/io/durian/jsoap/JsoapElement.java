package io.durian.jsoap;

import io.durian.Attribute;
import io.durian.Content;
import io.durian.Element;
import io.durian.Namespace;
import io.durian.immutable.ImmutableAttribute;
import io.durian.immutable.ImmutableText;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.durian.util.Serializer.serialize;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;

class JsoapElement implements Element {
    final String id = randomUUID().toString();
    final String name;
    final List<Content> content;
    final List<Attribute> attributes;
    final Element parent;

    JsoapElement(org.jsoup.nodes.Element jsoapElement, Element parent) {
        this.name = jsoapElement.nodeName();
        this.parent = parent;
        this.content = createContent(jsoapElement.childNodes());
        this.attributes = createAttributes(jsoapElement.attributes());
    }

    List<Content> createContent(List<Node> childNodes) {
        return childNodes.stream()
                .map(this::createContent)
                .filter(Objects::nonNull)
                .toList();
    }

    Content createContent(Node node) {
        if (node instanceof org.jsoup.nodes.Element element)
            return new JsoapElement(element, this);
        else if (node instanceof TextNode textNode) {
            String wholeText = textNode.getWholeText();
            if (!wholeText.trim().isBlank())
                return new ImmutableText(
                        randomUUID().toString(),
                        wholeText,
                        of(this)
                );
        } else if (node instanceof DataNode dataNode)
            return new ImmutableText(
                    randomUUID().toString(),
                    dataNode.getWholeData(),
                    of(this)
            );
        return null;
    }

    List<Attribute> createAttributes(Attributes attributes) {
        return attributes.asList()
                .stream()
                .map(this::createAttribute
                )
                .toList();
    }

    Attribute createAttribute(org.jsoup.nodes.Attribute attribute) {
        return new ImmutableAttribute(
                randomUUID().toString(),
                attribute.getKey(),
                attribute.getValue(),
                of(this),
                empty()
        );
    }

    @Override
    public List<Content> content() {
        return content;
    }

    @Override
    public List<Attribute> attributes() {
        return attributes;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Optional<Namespace> namespace() {
        return empty();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Optional<Element> parent() {
        return ofNullable(parent);
    }

    @Override
    public String toString() {
        return serialize(this);
    }
}
