package se.ugli.durian.w3c.soap;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;

public class Header extends MutableElement {

    public Header(final String name, final String uri, final NodeFactory nodeFactory,
            final Iterable<PrefixMapping> prefixMappings) {
        super(name, uri, nodeFactory, prefixMappings);
    }

}
