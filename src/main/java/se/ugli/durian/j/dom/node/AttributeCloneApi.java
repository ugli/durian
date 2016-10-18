package se.ugli.durian.j.dom.node;

public interface AttributeCloneApi {

    Attribute attribute();

    Attribute attribute(Element parent);

    Attribute attribute(Element parent, NodeFactory nodeFactory);

    Attribute attribute(Element parent, String attributeName);

    Attribute attribute(Element parent, String attributeName, NodeFactory nodeFactory);

    Attribute attribute(NodeFactory nodeFactory);

    Attribute attribute(String attributeName);

    Attribute attribute(String attributeName, NodeFactory nodeFactory);
}
