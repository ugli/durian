package io.durian.saxon;

import io.durian.Attribute;
import io.durian.Content;
import io.durian.Element;
import io.durian.Namespace;
import io.durian.Node;
import io.durian.util.Cache;
import io.durian.util.CacheFactory;
import lombok.SneakyThrows;
import net.sf.saxon.s9api.XPathExecutable;
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

public class SaxonElement implements Element {

    static final Cache<XPathExecutable> XPATH_CACHE = CacheFactory.create();

    final SaxonDocument document;
    final XdmNode xdmNode;

    SaxonElement(SaxonDocument document, XdmNode xdmNode) {
        this.document = document;
        this.xdmNode = xdmNode;
    }

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
                .map(n -> document.attribute(this, n))
                .toList();
    }

    Optional<Content> createElementContent(XdmNode xdmNode) {
        if (xdmNode.getNodeKind() == ELEMENT)
            return of(document.element(xdmNode));
        if (xdmNode.getNodeKind() == TEXT && !xdmNode.getStringValue().isBlank())
            return of(document.text(this, xdmNode));
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
        XPathExecutable executable = XPATH_CACHE.get(xpathExpr, this::createXPathExecutable);
        XPathSelector selector = executable.load();
        selector.setContextItem(xdmNode);
        return selector.stream()
                .map(n -> (XdmNode) n)
                .map(this::createNode)
                .toList();
    }

    @SneakyThrows
    private XPathExecutable createXPathExecutable(String expr) {
            return xdmNode.getProcessor()
                    .newXPathCompiler()
                    .compile(expr);
    }

    Node createNode(XdmNode xdmNode) {
        return switch (xdmNode.getNodeKind()) {
            case TEXT -> document.text(this, xdmNode);
            case ELEMENT -> document.element(xdmNode);
            case ATTRIBUTE -> document.attribute(this, xdmNode);
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public String id() {
        return randomUUID().toString();
    }

    @Override
    public Optional<Element> parent() {
        XdmNode parent = xdmNode.getParent();
        if (parent != null && parent.getNodeKind() != DOCUMENT)
            return of(document.element(parent));
        return empty();
    }
}
