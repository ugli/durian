package se.ugli.durian.j.fpd;

import static se.ugli.durian.j.schema.SchemaType.W3C_XML_SCHEMA;
import static se.ugli.durian.j.validation.Validator.validator;

import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.validation.Validator;

/**
 * Fixed Position Data Parser
 */
public class FpdParser {

	private static final Validator validator = validator(W3C_XML_SCHEMA,
			FpdParser.class.getResource("/durian/xsd/fpd.xsd"));

	private final Struct rootStruct;

	public FpdParser(final Element definitionElement) {
		if (validateDefinition(definitionElement))
			validator.validate(definitionElement.toXml().getBytes());
		rootStruct = Struct.apply(definitionElement, targetNamespace(definitionElement),
				includeEmptyValues(definitionElement), trimValues(definitionElement),
				arrayStartIndex(definitionElement));
	}

	private static int arrayStartIndex(final Element definitionElement) {
		return Integer.parseInt(definitionElement.attributeValue("arrayStartIndex").orElse("0"));
	}

	private static String targetNamespace(final Element definitionElement) {
		return definitionElement.attributeValue("targetNamespace").orElse(null);
	}

	private static boolean validateDefinition(final Element definitionElement) {
		return "true".equals(definitionElement.attributeValue("validateDefinition").orElse("true"));
	}

	private static boolean includeEmptyValues(final Element definitionElement) {
		return "true".equals(definitionElement.attributeValue("includeEmptyValues").orElse("false"));
	}

	private static boolean trimValues(final Element definitionElement) {
		return "true".equals(definitionElement.attributeValue("trimValues").orElse("true"));
	}

	public Element parse(final Source data) {
		return rootStruct.createNode(new String(data.data()))
				.orElseThrow(() -> new FpdException("No root struct found")).as(Element.class);
	}

}