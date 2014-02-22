package se.ugli.durian.j.core.nodeimpl;

import java.util.Collection;

import se.ugli.durian.j.core.Attribute;
import se.ugli.durian.j.core.Document;
import se.ugli.durian.j.core.Element;
import se.ugli.durian.j.core.Name;
import se.ugli.durian.j.core.NodeFactory;
import se.ugli.durian.j.core.PrefixMapping;
import se.ugli.durian.j.core.Text;

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
    public Element createElement(final Name name, final Document document, final Element parent,
	    final Collection<Attribute> attributes) {
	return new ElementImpl(name, document, parent, attributes);
    }

    @Override
    public Attribute createAttribute(final Name name, final String value) {
	return new AttributeImpl(name, value);
    }

    @Override
    public Text createText(final String value) {
	return new TextImpl(value);
    }

}
