package io.durian.model;

import java.util.Optional;

public interface Node {
    String id();
    Optional<? extends Element> parent();

    @SuppressWarnings("unchecked")
    default <T extends Node> T as(final Class<T> clazz) {
        return (T) this;
    }

    default String path() {
        return parent().map(Element::path).orElse("") + switch (this) {
            case Element e -> e.name();
            case Attribute a -> "@" + a.name();
            case Text ignored -> "text()";
            default -> throw new IllegalStateException();
        };
    }
}
