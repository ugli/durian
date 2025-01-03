package io.durian.immutable;

import io.durian.Attribute;
import io.durian.Element;
import io.durian.Namespace;
import lombok.Builder;

import java.util.Optional;

@Builder
public record ImmutableAttribute(String id,
                                 String name,
                                 String value,
                                 Optional<Element> parent,
                                 Optional<Namespace> namespace) implements Attribute {
}
