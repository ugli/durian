package se.ugli.durian.j.json.jackson.fieldvaluefactories;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.json.jackson.FieldValueFactory;

public class ByValueFieldValueFactory implements FieldValueFactory {

    @Override
    public Object create(final Attribute attribute) {
        return create(attribute.getValue());
    }

    @Override
    public Object create(final Text text) {
        return create(text.getValue());
    }

    private Object create(final String value) {
        if (isValueBoolean(value)) {
            return Boolean.parseBoolean(value);
        }
        else if (isValueLong(value)) {
            return Long.parseLong(value);
        }
        else if (isValueDouble(value)) {
            return Double.parseDouble(value);
        }
        return value;
    }

    private boolean isValueBoolean(final String value) {
        return value != null && (value.equals("true") || value.equals("false"));
    }

    private boolean isValueLong(final String value) {
        if (value != null) {
            try {
                Long.parseLong(value);
                return true;
            }
            catch (final NumberFormatException e) {
            }
        }
        return false;
    }

    private boolean isValueDouble(final String value) {
        if (value != null) {
            try {
                Double.parseDouble(value);
                return true;
            }
            catch (final NumberFormatException e) {
            }
        }
        return false;
    }

}
