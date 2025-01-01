package io.durian;

import java.util.Optional;

public interface NamedNode extends Node {
    String name();
    Optional<? extends Namespace> namespace();
}
