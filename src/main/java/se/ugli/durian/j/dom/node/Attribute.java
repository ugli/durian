package se.ugli.durian.j.dom.node;

import java.util.Optional;

public interface Attribute extends Node {

	AttributeCloneApi clone();

	String name();

	String qName();

	Optional<String> uri();

	String value();

}
