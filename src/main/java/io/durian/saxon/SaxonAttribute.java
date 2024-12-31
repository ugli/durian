package io.durian.saxon;

import io.durian.dom.Attribute;
import io.durian.dom.Element;
import io.durian.dom.Namespace;
import net.sf.saxon.s9api.XdmNode;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.UUID.randomUUID;

public record SaxonAttribute(XdmNode xdmNode) implements Attribute {
    @Override
    public String name() {
        return xdmNode.getNodeName().getLocalName();
    }

    @Override
    public Optional<? extends Namespace> namespace() {
        return SaxonNamespace.fromNode(xdmNode);
    }

    @Override
    public String value() {
        return xdmNode.getStringValue();
    }

    @Override
    public String id() {
        return randomUUID().toString();
    }

    @Override
    public Optional<? extends Element> parent() {
        return of(new SaxonElement(xdmNode.getParent()));
    }
}
