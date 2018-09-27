package se.ugli.durian.j.schema;

import org.xml.sax.SAXException;

public class SchemaException extends RuntimeException {

	private static final long serialVersionUID = 3843462301201792170L;

	public SchemaException(final SAXException e) {
		super(e);
	}

}
