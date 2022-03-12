package io.durian.dom;

import java.util.Optional;

public interface Node {
    String id();
    Optional<? extends Element> parent();
    String path();

    default Element asElement() {
        return (Element) this;
    }

    default Attribute asAttribute() {
        return (Attribute) this;
    }

    default Text asText() {
        return (Text) this;
    }
}
