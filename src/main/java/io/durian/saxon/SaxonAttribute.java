package io.durian.saxon;

import io.durian.Attribute;
import io.durian.Element;
import io.durian.Namespace;
import net.sf.saxon.s9api.XdmNode;

import java.util.Optional;

import static java.util.UUID.randomUUID;

public record SaxonAttribute(Optional<Element> parent, XdmNode xdmNode) implements Attribute {
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

}
