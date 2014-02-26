package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Name;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.node.Text;

public class MutableNodeFactory implements NodeFactory {

    @Override
    public PrefixMapping createPrefixMapping(final String prefix, final String uri) {
        return new MutablePrefixMapping(prefix, uri);
    }

    @Override
    public Name createName(final String uri, final String localName) {
        return new MutableName(uri, localName);
    }

    @Override
    public Document createDocument() {
        return new MutableDocument();
    }

    @Override
    public Element createElement(final Document document, final Element parent, final Name name) {
        return new MutableElement(document, parent, name);
    }

    @Override
    public Attribute createAttribute(final Element parent, final Name name, final String value) {
        return new MutableAttribute(parent, name, value);
    }

    @Override
    public Text createText(final Element element, final String value) {
        return new MutableText(element, value);
    }

}
