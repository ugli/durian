package io.durian.saxon;

import io.durian.dom.Attribute;
import io.durian.dom.Content;
import io.durian.dom.Element;
import io.durian.dom.Namespace;
import io.durian.dom.Node;
import lombok.SneakyThrows;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmNode;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.UUID.randomUUID;
import static java.util.stream.StreamSupport.stream;
import static net.sf.saxon.s9api.Axis.ATTRIBUTE;
import static net.sf.saxon.s9api.XdmNodeKind.DOCUMENT;
import static net.sf.saxon.s9api.XdmNodeKind.ELEMENT;
import static net.sf.saxon.s9api.XdmNodeKind.TEXT;

public record SaxonElement(XdmNode xdmNode) implements Element {

    @Override
    public List<Content> content() {
        return stream(xdmNode.children().spliterator(), false)
                .map(this::createElementContent)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public List<? extends Attribute> attributes() {
        return xdmNode.axisIterator(ATTRIBUTE).stream()
                .map(SaxonAttribute::new)
                .toList();
    }

    Optional<Content> createElementContent(XdmNode xdmNode) {
        if (xdmNode.getNodeKind() == ELEMENT)
            return of(new SaxonElement(xdmNode));
        if (xdmNode.getNodeKind() == TEXT && !xdmNode.getStringValue().isBlank())
            return of(new SaxonText(xdmNode));
        return empty();
    }

    @Override
    public String name() {
        return xdmNode.getNodeName().getLocalName();
    }

    @Override
    public Optional<? extends Namespace> namespace() {
        return SaxonNamespace.fromNode(xdmNode);
    }


    @SneakyThrows
    @Override
    public List<Node> select(String xpathExpr) {
        XPathSelector selector = xdmNode.getProcessor()
                .newXPathCompiler()
                .compile(xpathExpr)
                .load();
        selector.setContextItem(xdmNode);
        return selector.stream().map(n -> (XdmNode) n)
                .map(this::createNode)
                .toList();
    }

    Node createNode(XdmNode xdmNode) {
        return switch (xdmNode.getNodeKind()) {
            case TEXT -> new SaxonText(xdmNode);
            case ELEMENT -> new SaxonElement(xdmNode);
            case ATTRIBUTE -> new SaxonAttribute(xdmNode);
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public String id() {
        return randomUUID().toString();
    }

    @Override
    public Optional<? extends Element> parent() {
        XdmNode parent = xdmNode.getParent();
        if (parent != null && parent.getNodeKind() != DOCUMENT)
            return of(new SaxonElement(parent));
        return empty();
    }
}
