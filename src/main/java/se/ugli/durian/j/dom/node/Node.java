package se.ugli.durian.j.dom.node;

import java.util.Optional;

public interface Node {

    Optional<Element> getParent();

    String getPath();

    String id();

    NodeFactory nodeFactory();

    @SuppressWarnings("unchecked")
    default <T extends Node> T as(final Class<T> clazz) {
        return (T) this;
    }

}
