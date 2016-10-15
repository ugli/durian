package se.ugli.durian.j.dom.node;

import java.util.Optional;

public interface Attribute extends Node {

    String getName();

    Optional<String> getUri();

    String getValue();

    String qName();

    AttributeCloneApi clone();

}
