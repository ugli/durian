package io.durian.immutable;

import io.durian.Namespace;

public record ImmutableNamespace(String uri, String prefix) implements Namespace {
}
