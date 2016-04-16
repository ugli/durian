package se.ugli.durian.j.fpd;

import static se.ugli.durian.j.schema.SchemaType.W3C_XML_SCHEMA;
import static se.ugli.durian.j.validation.Validator.validator;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.parser.Parser;
import se.ugli.durian.j.validation.Validator;

public class FpdParser {

    private static final Validator validator = validator(W3C_XML_SCHEMA, FpdParser.class.getResource("/durian/xsd/fpd.xsd"));

    public static FpdParser apply(final byte[] defBytes) {
        return new FpdParser(defBytes);
    }

    private final Struct struct;
    private final String targetNamespace;

    private FpdParser(final byte[] definition) {
        validator.validate(definition);
        final Element element = Parser.apply().parse(definition);
        final boolean includeEmptyValues = Boolean.parseBoolean(element.getAttributeValue("includeEmptyValues"));
        targetNamespace = element.getAttributeValue("targetNamespace");
        struct = Struct.apply(element, targetNamespace, includeEmptyValues);
    }

    public Element parse(final String data) {
        return (Element) struct.createNode(data);
    }

}