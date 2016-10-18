package se.ugli.durian.j.dom.node;

public interface ElementCloneApi {

    Element element();

    Element element(Element parent);

    Element element(Element parent, NodeFactory nodeFactory);

    Element element(Element parent, String elementName);

    Element element(Element parent, String elementName, NodeFactory nodeFactory);

    Element element(NodeFactory nodeFactory);

    Element element(String elementName);

    Element element(String elementName, NodeFactory nodeFactory);

}
