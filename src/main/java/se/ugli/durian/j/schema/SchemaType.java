package se.ugli.durian.j.schema;

import static javax.xml.XMLConstants.RELAXNG_NS_URI;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import javax.xml.validation.SchemaFactory;

public enum SchemaType {
	W3C_XML_SCHEMA(W3C_XML_SCHEMA_NS_URI), RELAXNG(RELAXNG_NS_URI);

	public final String schemaLanguage;

	private SchemaType(final String schemaLanguage) {
		this.schemaLanguage = schemaLanguage;
	}

	public SchemaFactory schemaFactory() {
		return SchemaFactory.newInstance(schemaLanguage);
	}
}
