package se.ugli.durian.j.dom.node;

public class Prefixmapping {

    public final String prefix;
    public final String uri;

    public Prefixmapping(final String prefix, final String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Prefixmapping [prefix=" + prefix + ", uri=" + uri + "]";
    }

}
