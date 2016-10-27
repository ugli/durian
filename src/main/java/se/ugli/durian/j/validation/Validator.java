package se.ugli.durian.j.validation;

import static se.ugli.durian.j.schema.Schemas.schema;
import static se.ugli.durian.j.validation.ValidationErrorType.ERROR;
import static se.ugli.durian.j.validation.ValidationErrorType.FATAL_ERROR;
import static se.ugli.durian.j.validation.ValidationErrorType.WARNING;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import se.ugli.durian.j.schema.SchemaType;

public class Validator {

    private final Schema schema;

    private Validator(final Schema schema) {
        this.schema = schema;
    }

    public static Validator validator(final SchemaType type, final byte[] schema) {
        return new Validator(schema(type, schema));
    }

    public static Validator validator(final SchemaType type, final URL schema) {
        return new Validator(schema(type, schema));
    }

    public static Validator validator(final SchemaType type, final InputStream schema) {
        return new Validator(schema(type, schema));
    }

    public void validate(final byte[] xmlBytes) {
        final javax.xml.validation.Validator validator = schema.newValidator();
        final ValidationExceptionBuilder errorHandler = new ValidationExceptionBuilder();
        validator.setErrorHandler(errorHandler);
        try {
            validator.validate(new StreamSource(new ByteArrayInputStream(xmlBytes)));
            if (!errorHandler.errors.isEmpty())
                throw errorHandler.build();
        }
        catch (final SAXException e) {
            throw new ValidationException(e);
        }
        catch (final IOException e) {
            throw new ValidationException(e);
        }
    }

    private static class ValidationExceptionBuilder implements ErrorHandler {

        List<ValidationError> errors = new ArrayList<ValidationError>();

        @Override
        public void warning(final SAXParseException exception) throws SAXException {
            errors.add(new ValidationError(WARNING, exception.getMessage()));
        }

        @Override
        public void error(final SAXParseException exception) throws SAXException {
            errors.add(new ValidationError(ERROR, exception.getMessage()));
        }

        @Override
        public void fatalError(final SAXParseException exception) throws SAXException {
            errors.add(new ValidationError(FATAL_ERROR, exception.getMessage()));
        }

        ValidationException build() {
            return new ValidationException(errors);
        }

    }

}
