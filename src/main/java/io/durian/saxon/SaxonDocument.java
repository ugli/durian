package io.durian.saxon;

import io.durian.dom.Attribute;
import io.durian.dom.Element;
import io.durian.dom.Text;
import net.sf.saxon.s9api.XdmNode;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.of;

class SaxonDocument {

    final XdmNode xdmDocument;
    final Map<Integer, Element> elementByXdmNodeHashCode = new HashMap<>();
    final Map<Integer, Text> textByXdmNodeHashCode = new HashMap<>();
    final Map<Integer, Attribute> attributeByXdmNodeHashCode = new HashMap<>();

    SaxonDocument(XdmNode xdmDocument) {
        this.xdmDocument = xdmDocument;
    }

    Element root() {
        return new SaxonElement(this, xdmDocument.children().iterator().next());
    }

    Element element(XdmNode xdmNode) {
        return elementByXdmNodeHashCode.computeIfAbsent(
                xdmNode.hashCode(),
                k -> new SaxonElement(this, xdmNode)
        );
    }

    Attribute attribute(Element element, XdmNode xdmNode) {
        return attributeByXdmNodeHashCode.computeIfAbsent(
                xdmNode.hashCode(),
                k -> new SaxonAttribute(of(element), xdmNode)
        );
    }

    Text text(Element element, XdmNode xdmNode) {
        return textByXdmNodeHashCode.computeIfAbsent(
                xdmNode.hashCode(),
                k -> new SaxonText(of(element), xdmNode)
        );
    }
}
