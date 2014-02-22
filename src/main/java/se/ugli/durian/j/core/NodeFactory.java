package se.ugli.durian.j.core;

import java.util.Collection;

public interface NodeFactory {

    PrefixMapping createPrefixMapping(String prefix, String uri);

    Name createName(String uri, String localName, String qName);

    Document createDocument();

    Element createElement(Name name, Document document, Element parent, Collection<Attribute> attributes);

    Attribute createAttribute(Name name, String value);

    Text createText(String value);

}
