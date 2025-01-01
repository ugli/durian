package io.durian.immutable;

import java.util.Optional;

import io.durian.Element;
import io.durian.Text;

public record ImmutableText(String id, String value, Optional<Element> parent) implements Text {
}
