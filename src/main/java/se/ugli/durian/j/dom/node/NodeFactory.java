package se.ugli.durian.j.dom.node;

public interface NodeFactory {

    <T extends Attribute> T createAttribute(String name, String uri, Element parent, String value);

    <T extends Element> T createElement(String name, String uri, Element parent, Iterable<PrefixMapping> prefixMappings);

    <T extends Text> T createText(Element parent, String value);

}
