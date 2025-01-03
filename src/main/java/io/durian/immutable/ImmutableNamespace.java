package io.durian.immutable;

import io.durian.Namespace;
import lombok.Builder;

@Builder
public record ImmutableNamespace(String uri, String prefix) implements Namespace {
}
