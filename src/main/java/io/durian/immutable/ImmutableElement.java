package io.durian.immutable;

import io.durian.Attribute;
import io.durian.Content;
import io.durian.Element;
import io.durian.Namespace;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Collections.unmodifiableList;

public record ImmutableElement(String id,
                               String name,
                               Supplier<List<Content>> contentSupplier,
                               Supplier<List<Attribute>> attributesSupplier,
                               Optional<Element> parent,
                               Optional<Namespace> namespace) implements Element {
    @Override
    public List<Content> content() {
        return unmodifiableList(contentSupplier.get());
    }

    @Override
    public List<Attribute> attributes() {
        return unmodifiableList(attributesSupplier.get());
    }

}
