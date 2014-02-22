package se.ugli.durian.j.dom.node;

public interface NodeFactory {

    PrefixMapping createPrefixMapping(String prefix, String uri);

    Name createName(String uri, String localName, String qName);

    Document createDocument();

    Element createElement(Document document, Element parent, Name name);

    Attribute createAttribute(Element parent, Name name, String value);

    Text createText(Element parent, String value);

}
