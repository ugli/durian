package io.durian.immutable;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.UUID.randomUUID;
import io.durian.dom.Element;

public record Text(String id, String value, Optional<Element> parent) implements io.durian.dom.Text {

    public Text(String value, Element parent) {
        this(randomUUID().toString(), value, of(parent));
    }

    @Override
    public String toString() {
        return value;
    }
}
