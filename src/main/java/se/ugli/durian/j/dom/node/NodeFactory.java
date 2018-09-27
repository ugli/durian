package se.ugli.durian.j.dom.node;

public interface NodeFactory {

	Attribute createAttribute(String name, String uri, Element parent, String value);

	Element createElement(String name, String uri, Element parent, Iterable<PrefixMapping> prefixMappings);

	Text createText(Element parent, String value);

}
