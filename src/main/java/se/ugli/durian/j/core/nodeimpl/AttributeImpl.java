package se.ugli.durian.j.core.nodeimpl;

import se.ugli.durian.j.core.Attribute;
import se.ugli.durian.j.core.Name;

public class AttributeImpl implements Attribute {

    private final Name name;
    private final String value;

    public AttributeImpl(final Name name, final String value) {
	this.name = name;
	this.value = value;
    }

    @Override
    public Name getName() {
	return name;
    }

    @Override
    public String getValue() {
	return value;
    }

}
