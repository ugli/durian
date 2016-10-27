package se.ugli.durian.j.fpd;

import static se.ugli.durian.j.schema.SchemaType.W3C_XML_SCHEMA;
import static se.ugli.durian.j.validation.Validator.validator;

import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.parser.XmlParserBuilder;
import se.ugli.durian.j.validation.Validator;

public class FpdParser {

    private static final Validator validator = validator(W3C_XML_SCHEMA,
            FpdParser.class.getResource("/durian/xsd/fpd.xsd"));

    private final Struct struct;
    private final String targetNamespace;

    public FpdParser(final Source definition, final XmlParserBuilder xmlParserBuilder) {
        validator.validate(definition.data());
        final Element element = xmlParserBuilder.build().parse(definition);
        final boolean includeEmptyValues = Boolean.parseBoolean(element.attributeValue("includeEmptyValues").get());
        targetNamespace = element.attributeValue("targetNamespace").orElse(null);
        struct = Struct.apply(element, targetNamespace, includeEmptyValues);
    }

    public Element parse(final String data) {
        return struct.createNode(data).as(Element.class);
    }

}