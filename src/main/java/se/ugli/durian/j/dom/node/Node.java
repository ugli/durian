package se.ugli.durian.j.dom.node;

import java.util.Optional;

public interface Node {

    <T extends Element> Optional<T> getParent();

    String getPath();

    String id();

    NodeFactory nodeFactory();
}
