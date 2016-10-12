package se.ugli.durian.j.dom.node;

public interface AttributeCloneApi {

    <T extends Attribute> T attribute();

    <T extends Attribute> T attribute(Element parent);

    <T extends Attribute> T attribute(NodeFactory nodeFactory);

    <T extends Attribute> T attribute(Element parent, NodeFactory nodeFactory);

    <T extends Attribute> T attribute(String attributeName);

    <T extends Attribute> T attribute(Element parent, String attributeName);

    <T extends Attribute> T attribute(String attributeName, NodeFactory nodeFactory);

    <T extends Attribute> T attribute(Element parent, String attributeName, NodeFactory nodeFactory);
}
