package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;

public class MutableNodeFactory implements NodeFactory {

    @Override
    public MutableAttribute createAttribute(final String name, final String uri, final Element parent,
            final String value) {
        return new MutableAttribute(name, uri, value, this);
    }

    @Override
    public MutableElement createElement(final String name, final String uri, final Element parent,
            final Iterable<PrefixMapping> prefixMappings) {
        return new MutableElement(name, uri, this, prefixMappings);
    }

    @Override
    public MutableText createText(final Element element, final String value) {
        return new MutableText(value, this);
    }

}
