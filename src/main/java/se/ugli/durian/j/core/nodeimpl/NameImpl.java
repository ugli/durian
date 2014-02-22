package se.ugli.durian.j.core.nodeimpl;

import se.ugli.durian.j.core.Name;

public class NameImpl implements Name {

    private final String uri;
    private final String localName;
    private final String qName;

    public NameImpl(final String uri, final String localName, final String qName) {
	this.uri = uri;
	this.localName = localName;
	this.qName = qName;
    }

    @Override
    public String getUri() {
	return uri;
    }

    @Override
    public String getLocalName() {
	return localName;
    }

    @Override
    public String getQName() {
	return qName;
    }

}
