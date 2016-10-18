package se.ugli.durian.j.json.jackson.fieldvaluefactories;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.json.jackson.FieldValueFactory;

public class StringFieldValueFactory implements FieldValueFactory {

    @Override
    public Object create(final Attribute attribute) {
        return attribute.value();
    }

    @Override
    public Object create(final Text text) {
        return text.value();
    }

}
