package io.durian.immutable;

import io.durian.Attribute;
import io.durian.Content;
import io.durian.Element;
import io.durian.Namespace;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

public record ImmutableElement(String id,
                               String name,
                               List<Content> content,
                               List<Attribute> attributes,
                               Optional<Element> parent,
                               Optional<Namespace> namespace) implements Element {
    @Override
    public List<Content> content() {
        return unmodifiableList(content);
    }

    @Override
    public List<Attribute> attributes() {
        return unmodifiableList(attributes);
    }

}
