package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Name;

public class MutableName implements Name {

    private final String uri;
    private final String localName;

    public MutableName(final String uri, final String localName) {
        this.uri = uri;
        this.localName = localName;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getLocalName() {
        return localName;
    }

}
