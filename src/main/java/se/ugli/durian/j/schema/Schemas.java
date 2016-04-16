package se.ugli.durian.j.schema;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

public class Schemas {

    public static Schema schema(final SchemaType type, final byte[] schema) {
        return schema(type, new ByteArrayInputStream(schema));
    }

    public static Schema schema(final SchemaType type, final InputStream inputStream) {
        try {
            return type.schemaFactory().newSchema(new StreamSource(inputStream));
        }
        catch (final SAXException e) {
            throw new SchemaException(e);
        }
    }

    public static Schema schema(final SchemaType type, final URL schema) {
        try {
            return type.schemaFactory().newSchema(schema);
        }
        catch (final SAXException e) {
            throw new SchemaException(e);
        }
    }

}
