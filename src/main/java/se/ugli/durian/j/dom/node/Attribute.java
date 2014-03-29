package se.ugli.durian.j.dom.node;

public interface Attribute extends Node {

    <T extends Attribute> T cloneAttribute();

    <T extends Attribute> T cloneAttribute(NodeFactory nodeFactory);

    <T extends Attribute> T cloneAttribute(String attributeName);

    <T extends Attribute> T cloneAttribute(String attributeName, NodeFactory nodeFactory);

    String getName();

    String getUri();

    String getValue();

    void setValue(String value);

}
