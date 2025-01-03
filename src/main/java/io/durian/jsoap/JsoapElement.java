package io.durian.jsoap;

import io.durian.Attribute;
import io.durian.Content;
import io.durian.Element;
import io.durian.Namespace;
import io.durian.immutable.ImmutableAttribute;
import io.durian.immutable.ImmutableText;
import io.durian.jaxen.DurianNavigator;
import lombok.SneakyThrows;
import org.jaxen.BaseXPath;
import org.jaxen.Navigator;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.List;
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
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    Optional<Content> createContent(Node node) {
        if (node instanceof org.jsoup.nodes.Element element)
            return of(new JsoapElement(element, this));
        else if (node instanceof TextNode textNode) {
            String wholeText = textNode.getWholeText();
            if (!wholeText.trim().isBlank())
                return of(ImmutableText.builder()
                        .id(randomUUID().toString())
                        .value(wholeText)
                        .parent(of(this))
                        .build());
        } else if (node instanceof DataNode dataNode)
            return of(ImmutableText.builder()
                    .id(randomUUID().toString())
                    .value(dataNode.getWholeData())
                    .parent(of(this))
                    .build());
        return empty();
    }

    List<Attribute> createAttributes(Attributes attributes) {
        return attributes.asList()
                .stream()
                .map(this::createAttribute)
                .toList();
    }

    Attribute createAttribute(org.jsoup.nodes.Attribute attribute) {
        return ImmutableAttribute.builder()
                .id(randomUUID().toString())
                .name(attribute.getKey())
                .value(attribute.getValue())
                .parent(of(this))
                .namespace(empty())
                .build();
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

    @SneakyThrows
    @Override
    public List<io.durian.Node> select(String xpathExpr) {
        Navigator navigator = new DurianNavigator(this);
        BaseXPath xPath = new BaseXPath(xpathExpr, navigator);
        return xPath.selectNodes(this);
    }
}
