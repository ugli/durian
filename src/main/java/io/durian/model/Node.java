package io.durian.model;

import java.util.Optional;

public interface Node {
    String id();
    Optional<? extends Element> parent();
    @SuppressWarnings("unchecked")
    default <T extends Node> T as(final Class<T> clazz) {
        return (T) this;
    }
    String path();
}
