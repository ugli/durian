package io.durian.saxon;

import io.durian.Attribute;
import io.durian.Element;
import io.durian.Text;
import net.sf.saxon.s9api.XdmNode;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.of;

public class SaxonDocument {

    final XdmNode xdmDocument;
    final Map<Integer, Element> elementByXdmNodeHashCode = new HashMap<>();
    final Map<Integer, Text> textByXdmNodeHashCode = new HashMap<>();
    final Map<Integer, Attribute> attributeByXdmNodeHashCode = new HashMap<>();

    public SaxonDocument(XdmNode xdmDocument) {
        this.xdmDocument = xdmDocument;
    }

    public Element root() {
        return new SaxonElement(this, xdmDocument.children().iterator().next());
    }

    Element element(XdmNode xdmNode) {
        return elementByXdmNodeHashCode.computeIfAbsent(
                xdmNode.hashCode(),
                k -> new SaxonElement(this, xdmNode)
        );
    }

    Attribute attribute(Element parent, XdmNode xdmNode) {
        return attributeByXdmNodeHashCode.computeIfAbsent(
                xdmNode.hashCode(),
                k -> new SaxonAttribute(of(parent), xdmNode)
        );
    }

    Text text(Element parent, XdmNode xdmNode) {
        return textByXdmNodeHashCode.computeIfAbsent(
                xdmNode.hashCode(),
                k -> new SaxonText(of(parent), xdmNode)
        );
    }
}
