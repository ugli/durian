package se.ugli.durian.j.dom.node;

public interface NodeFactory {

    PrefixMapping createPrefixMapping(String prefix, String uri);

    Document createDocument();

    Element createElement(String name, String uri, Document document, Element parent);

    Attribute createAttribute(String name, String uri, Element parent, String value);

    Text createText(Element parent, String value);

}
