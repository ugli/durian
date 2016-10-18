package se.ugli.durian.j.dom.node;

public interface ElementCloneApi {

    Element element();

    Element element(NodeFactory nodeFactory);

    Element element(String elementName);

    Element element(String elementName, NodeFactory nodeFactory);

}
