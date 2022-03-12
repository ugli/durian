package io.durian.immutable;

import io.durian.dom.Content;
import io.durian.util.Serializer;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;

public record Element(String id,
                      String name,
                      Supplier<List<Content>> contentSupplier,
                      Supplier<List<Attribute>> attributesSupplier,
                      Optional<Element> parent,
                      Optional<Namespace> namespace) implements io.durian.dom.Element {

    Element(String name,
            Supplier<List<Content>> contentSupplier,
            Supplier<List<Attribute>> attributesSupplier,
            Element parent,
            Namespace namespace) {
        this(randomUUID().toString(),
                name,
                contentSupplier,
                attributesSupplier,
                ofNullable(parent),
                ofNullable(namespace));
    }

    @Override
    public List<Content> content() {
        return contentSupplier.get().stream().toList();
    }

    @Override
    public List<Attribute> attributes() {
        return attributesSupplier.get().stream().toList();
    }

    @Override
    public String toString() {
        return Serializer.serialize(this);
    }
}
