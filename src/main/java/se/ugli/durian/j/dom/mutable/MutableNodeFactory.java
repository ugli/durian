package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class MutableNodeFactory implements NodeFactory {

    @Override
    public Document createDocument() {
        return new MutableDocument();
    }

    @Override
    public Element createElement(final String name, final String uri, final Document document, final Element parent) {
        if (parent == null) {
            return new MutableElement(name, uri, document);
        }
        else {
            return new MutableElement(name, uri);
        }
    }

    @Override
    public Attribute createAttribute(final String name, final String uri, final Element parent, final String value) {
        return new MutableAttribute(name, uri, parent, value);
    }

    @Override
    public Text createText(final Element element, final String value) {
        return new MutableText(element, value);
    }

}
