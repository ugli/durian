package se.ugli.durian.j.fpd;

import static se.ugli.durian.j.dom.node.PrefixMapping.prefixMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.PrefixMapping;

class Struct implements Definition {

    private final String name;
    private final String targetNamespace;
    private final boolean includeEmptyValues;
    private final boolean root;
    private final List<Definition> definitions = new ArrayList<>();
    private final int numOfChars;
    private final boolean trimValues;
    private final int arrayStartIndex;

    private Struct(final Element structElementDefinition, final String targetNamespace,
            final boolean includeEmptyValues, final boolean root, final boolean trimValues, final int arrayStartIndex) {
        this.trimValues = trimValues;
        this.arrayStartIndex = arrayStartIndex;
        this.targetNamespace = targetNamespace;
        this.includeEmptyValues = includeEmptyValues;
        this.root = root;
        name = structName(structElementDefinition);
        final AtomicInteger numOfCharsAccumlator = new AtomicInteger(0);
        structElementDefinition.elements().forEach(e -> {
            final Definition definition = definition(e);
            definitions.add(definition);
            numOfCharsAccumlator.addAndGet(definition.numOfChars());
        });
        numOfChars = numOfCharsAccumlator.get();
    }

    private static String structName(final Element structElementDefinition) {
        return structElementDefinition.attributeValue("name")
                .orElseThrow(() -> new FpdException("Struct name missing"));
    }

    private Definition definition(final Element elementDefinition) {
        switch (defintionType(elementDefinition)) {
        case "Struct":
            return new Struct(elementDefinition, targetNamespace, includeEmptyValues, false, trimValues,
                    arrayStartIndex);
        case "Array":
            return new Array(elementDefinition, targetNamespace, includeEmptyValues, trimValues, arrayStartIndex);
        case "Field":
            return new Field(elementDefinition, targetNamespace, includeEmptyValues, trimValues);
        default:
            throw new IllegalStateException();
        }
    }

    private static String defintionType(final Element elementDefinition) {
        return elementDefinition.attributeValue("type").orElseThrow(() -> new FpdException("Definition type missing"));
    }

    static Struct apply(final Element element, final String targetNamespace, final boolean includeEmptyValues,
            final boolean trimValues, final int arrayStartIndex) {
        return new Struct(element, targetNamespace, includeEmptyValues, true, trimValues, arrayStartIndex);
    }

    @Override
    public Optional<Node> createNode(final String data) {
        final ArrayList<PrefixMapping> prefixmappings = new ArrayList<>();
        if (root && targetNamespace != null)
            prefixmappings.add(prefixMapping(null, targetNamespace));
        final MutableElement element = new MutableElement(name, targetNamespace, new MutableNodeFactory(),
                prefixmappings);
        int beginIndex = 0;
        for (final Definition definition : definitions) {
            final int endIndex = beginIndex + definition.numOfChars();
            definition.createNode(data.substring(beginIndex, endIndex)).ifPresent(element::add);
            beginIndex = endIndex;
        }
        return Optional.of(element);
    }

    @Override
    public int numOfChars() {
        return numOfChars;
    }

    @Override
    public String toString() {
        return "Struct [name=" + name + ", targetNamespace=" + targetNamespace + ", includeEmptyValues="
                + includeEmptyValues + ", root=" + root + ", definitions=" + definitions + ", numOfChars=" + numOfChars
                + "]";
    }

}
