package se.ugli.durian.j.dom.node;

import java.util.Optional;

public interface Node {

	@SuppressWarnings("unchecked")
	default <T extends Node> T as(final Class<T> clazz) {
		return (T) this;
	}

	String id();

	NodeFactory nodeFactory();

	Optional<Element> parent();

	String path();

}
