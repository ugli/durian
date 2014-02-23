package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.PrefixMapping;

public class MutablePrefixMapping implements PrefixMapping {

    private final String prefix;
    private final String uri;

    public MutablePrefixMapping(final String prefix, final String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getUri() {
        return uri;
    }

}
