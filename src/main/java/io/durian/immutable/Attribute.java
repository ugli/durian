package io.durian.immutable;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;

public record Attribute(String id,
                        String name,
                        String value,
                        Optional<Element> parent,
                        Optional<Namespace> namespace)
        implements io.durian.model.Attribute {

    Attribute(String name, String value, Element parent, Namespace namespace) {
        this(randomUUID().toString(), name, value, of(parent), ofNullable(namespace));
    }

    @Override
    public String toString() {
        return value;
    }
}
