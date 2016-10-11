package se.ugli.durian;

import static java.util.Arrays.asList;

import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.PrefixMapping;

public class Durian {

    public static <T extends Element> T createElement(final String name, final PrefixMapping... prefixMappings) {
        return new MutableNodeFactory().createElement(name, null, null, asList(prefixMappings));
    }

    public static <T extends Element> T createElement(final String name, final String uri, final PrefixMapping... prefixMappings) {
        return new MutableNodeFactory().createElement(name, uri, null, asList(prefixMappings));
    }

}
