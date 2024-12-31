package io.durian.saxon;

import io.durian.dom.Element;
import io.durian.dom.Text;
import net.sf.saxon.s9api.XdmNode;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.UUID.randomUUID;

public record SaxonText(XdmNode xdmNode) implements Text {

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
