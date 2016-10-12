package se.ugli.durian.j.dom.node;

public interface ElementCloneApi {

    <T extends Element> T element();

    <T extends Element> T element(NodeFactory nodeFactory);

    <T extends Element> T element(String elementName);

    <T extends Element> T element(String elementName, NodeFactory nodeFactory);

}
