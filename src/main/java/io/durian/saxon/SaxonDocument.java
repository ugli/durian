package io.durian.saxon;

import io.durian.dom.Element;
import net.sf.saxon.s9api.XdmNode;

import java.util.HashMap;
import java.util.Map;

class SaxonDocument {

    final XdmNode xdmDocument;
    final Map<Integer, Element> elementByXdmNodeHashCode = new HashMap<>();

    SaxonDocument(XdmNode xdmDocument) {
        this.xdmDocument = xdmDocument;
    }

    Element root() {
        return new SaxonElement(this, xdmDocument.children().iterator().next());
    }

    Element createElement(XdmNode xdmNode) {
        int hashCode = xdmNode.hashCode();
        if (elementByXdmNodeHashCode.containsKey(hashCode)) {
            return elementByXdmNodeHashCode.get(hashCode);
        }
        SaxonElement element = new SaxonElement(this, xdmNode);
        elementByXdmNodeHashCode.put(hashCode, element);
        return element;
    }


}
