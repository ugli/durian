package io.durian.model;

public interface Text extends Content, ValueNode {
    default String path() {
        return parent().map(Element::path).map(p -> p + "/").orElse("") + "/text()";
    }
}
