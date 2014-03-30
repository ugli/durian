package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.NodeFactory;

public interface MutableNode extends Node {

    void setParent(Element parent);

    void setNodeFactory(NodeFactory nodeFactory);

}
