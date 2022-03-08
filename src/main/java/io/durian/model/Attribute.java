package io.durian.model;

public interface Attribute extends NamedNode, ValueNode {
    default String path() {
        return parent().map(Element::path).map(p -> p + "/").orElse("") +  "@" + name();
    }
}
