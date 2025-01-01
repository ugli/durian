package io.durian.jsoap;

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
    private final String id = randomUUID().toString();
    private final String name;
    private final List<Content> content;
    private final List<ImmutableAttribute> attributes;
    private final JsoapElement parent;

    JsoapElement(org.jsoup.nodes.Element element, JsoapElement parent) {
        this.name = element.nodeName();
        this.parent = parent;
        this.content = mapContent(element.childNodes());
        this.attributes = mapAttributes(element.attributes());
    }

    private List<Content> mapContent(List<Node> childNodes) {
        return childNodes.stream()
                .map(this::mapContent)
                .filter(Objects::nonNull)
                .toList();
    }

    private Content mapContent(Node node) {
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
        } else if (node instanceof DataNode dataNode) {
            return new ImmutableText(
                    randomUUID().toString(),
                    dataNode.getWholeData(),
                    of(this)
            );
        }
        return null;
    }

    private List<ImmutableAttribute> mapAttributes(Attributes attributes) {
        return attributes.asList()
                .stream()
                .map(a -> new ImmutableAttribute(
                                randomUUID().toString(),
                                a.getKey(),
                                a.getValue(),
                                of(this),
                                empty()
                        )
                )
                .toList();
    }

    @Override
    public List<Content> content() {
        return content;
    }

    @Override
    public List<ImmutableAttribute> attributes() {
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
