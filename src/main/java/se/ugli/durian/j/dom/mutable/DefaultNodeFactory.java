package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Name;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.node.Text;

public class DefaultNodeFactory implements NodeFactory {

    @Override
    public PrefixMapping createPrefixMapping(final String prefix, final String uri) {
        return new PrefixMappingImpl(prefix, uri);
    }

    @Override
    public Name createName(final String uri, final String localName, final String qName) {
        return new NameImpl(uri, localName, qName);
    }

    @Override
    public Document createDocument() {
        return new DocumentImpl();
    }

    @Override
    public Element createElement(final Document document, final Element parent, final Name name) {
        return new ElementImpl(document, parent, name);
    }

    @Override
    public Attribute createAttribute(final Element parent, final Name name, final String value) {
        return new AttributeImpl(parent, name, value);
    }

    @Override
    public Text createText(final Element element, final String value) {
        return new TextImpl(element, value);
    }

}
