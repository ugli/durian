package se.ugli.durian.j.dom.mutable;

import java.util.List;

import se.ugli.durian.j.dom.node.Element;

public interface MutableElement extends Element {

    void setAttributeValue(String attributeName, String value);

    void setParent(final MutableElement parent);

    @Override
    MutableElement clone();

    @Override
    List<MutableElement> getElements();

}
