package io.durian.dom;

import java.util.Optional;

public interface NamedNode extends Node {
    String name();
    Optional<? extends Namespace> namespace();
}
