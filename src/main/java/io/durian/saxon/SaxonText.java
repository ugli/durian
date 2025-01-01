package io.durian.saxon;

import io.durian.Element;
import io.durian.Text;
import net.sf.saxon.s9api.XdmNode;

import java.util.Optional;

import static java.util.UUID.randomUUID;

public record SaxonText(Optional<Element> parent, XdmNode xdmNode) implements Text {

    @Override
    public String value() {
        return xdmNode.getStringValue();
    }

    @Override
    public String id() {
        return randomUUID().toString();
    }

}
