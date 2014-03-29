package se.ugli.durian.j.dom.node;

public interface Node {

    NodeFactory getNodeFactory();

    <T extends Element> T getParent();

    String getPath();
}
