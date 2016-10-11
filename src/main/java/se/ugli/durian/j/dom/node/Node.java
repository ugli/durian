package se.ugli.durian.j.dom.node;

public interface Node {

    <T extends Element> T getParent();

    String getPath();

    String id();
}
