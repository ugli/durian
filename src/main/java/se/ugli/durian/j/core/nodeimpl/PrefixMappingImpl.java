package se.ugli.durian.j.core.nodeimpl;

import se.ugli.durian.j.core.PrefixMapping;

public class PrefixMappingImpl implements PrefixMapping {

    private final String prefix;
    private final String uri;

    public PrefixMappingImpl(final String prefix, final String uri) {
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
