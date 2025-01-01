package io.durian.saxon;

import io.durian.Namespace;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.XdmNode;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public record SaxonNamespace(String uri, String prefix) implements Namespace {

    static Optional<Namespace> fromNode(XdmNode xdmNode) {
        QName nodeName = xdmNode.getNodeName();
        if (nodeName != null && nodeName.getNamespaceUri() != null) {
            String uri = nodeName.getNamespaceUri().toString();
            String prefix = nodeName.getPrefix();
            return of(new SaxonNamespace(uri, prefix));
        }
        return empty();
    }

}
