package se.ugli.durian.j.fpd;

import java.util.Optional;

import se.ugli.durian.j.dom.mutable.MutableAttribute;
import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;

class Field implements Definition {

	private final String name;
	private final int length;
	private final String targetNamespace;
	private final boolean includeEmptyValues;
	private final boolean trimValues;

	Field(final Element fieldElementDefinition, final String targetNamespace, final boolean includeEmptyValues,
			final boolean trimValues) {
		this.trimValues = trimValues;
		this.targetNamespace = targetNamespace;
		this.includeEmptyValues = includeEmptyValues;
		name = fieldName(fieldElementDefinition);
		length = fieldLength(fieldElementDefinition);
	}

	private static int fieldLength(final Element fieldElementDefinition) {
		return Integer.parseInt(fieldElementDefinition.attributeValue("length")
				.orElseThrow(() -> new FpdException("Field length missing")));
	}

	private static String fieldName(final Element fieldElementDefinition) {
		return fieldElementDefinition.attributeValue("name").orElseThrow(() -> new FpdException("Field name missing"));
	}

	@Override
	public Optional<Node> createNode(final String data) {
		final String value = trimValues ? data.trim() : data;
		if (!value.isEmpty() || includeEmptyValues)
			return Optional.of(new MutableAttribute(name, targetNamespace, value, new MutableNodeFactory()));
		return Optional.empty();
	}

	@Override
	public int numOfChars() {
		return length;
	}

	@Override
	public String toString() {
		return "Field [name=" + name + ", length=" + length + ", targetNamespace=" + targetNamespace
				+ ", includeEmptyValues=" + includeEmptyValues + ", trimValues=" + trimValues + "]";
	}

}
