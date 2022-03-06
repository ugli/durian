package io.durian.immutable;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.UUID.randomUUID;

public record Text(String id, String value, Optional<Element> parent) implements io.durian.model.Text {

    Text(String value, Element parent) {
        this(randomUUID().toString(), value, of(parent));
    }

    @Override
    public String toString() {
        return value;
    }
}
