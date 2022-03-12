package io.durian.immutable;

public record Namespace(String uri, String prefix) implements io.durian.dom.Namespace {
}
