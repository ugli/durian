package se.ugli.durian.j.fpd;

import java.util.ArrayList;
import java.util.Optional;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.PrefixMapping;

class Array implements Definition {

	private final String name;
	private final int size;
	private final int elementLength;
	private final String targetNamespace;
	private final boolean includeEmptyValues;
	private final boolean trimValues;
	private final int arrayStartIndex;

	Array(final Element arrayElementDefinition, final String targetNamespace, final boolean includeEmptyValues,
			final boolean trimValues, final int arrayStartIndex) {
		this.targetNamespace = targetNamespace;
		this.includeEmptyValues = includeEmptyValues;
		this.trimValues = trimValues;
		this.arrayStartIndex = arrayStartIndex;
		name = arrayName(arrayElementDefinition);
		size = arraySize(arrayElementDefinition);
		elementLength = elementLength(arrayElementDefinition);
	}

	private static String arrayName(final Element elementDefinition) {
		return elementDefinition.attributeValue("name").orElseThrow(() -> new FpdException("Array name missing"));
	}

	private static int arraySize(final Element elementDefinition) {
		return Integer.parseInt(
				elementDefinition.attributeValue("size").orElseThrow(() -> new FpdException("Array size missing")));
	}

	private static int elementLength(final Element elementDefinition) {
		return Integer.parseInt(elementDefinition.attributeValue("elementLength")
				.orElseThrow(() -> new FpdException("Array element length missing")));
	}

	@Override
	public Optional<Node> createNode(final String data) {
		final MutableElement element = new MutableElement(name, targetNamespace, new MutableNodeFactory(),
				new ArrayList<PrefixMapping>());
		int beginIndex = 0;
		for (int i = 0; i < size; i++) {
			final int endIndex = beginIndex + elementLength;
			final String subStr = data.substring(beginIndex, endIndex);
			final String value = trimValues ? subStr.trim() : subStr;
			final MutableElement e = element.addElement("element").as(MutableElement.class);
			e.addAttribute("index", String.valueOf(i + arrayStartIndex));
			if (!value.isEmpty() || includeEmptyValues)
				e.addAttribute("value", value);
			beginIndex = endIndex;
		}
		return Optional.of(element);
	}

	@Override
	public int numOfChars() {
		return size * elementLength;
	}

	@Override
	public String toString() {
		return "Array [name=" + name + ", size=" + size + ", elementLength=" + elementLength + ", targetNamespace="
				+ targetNamespace + ", includeEmptyValues=" + includeEmptyValues + ", trimValues=" + trimValues
				+ ", arrayStartIndex=" + arrayStartIndex + "]";
	}

}
