package se.ugli.durian.j.dom.node;

public class Prefixmapping {

    public final String prefix;
    public final String uri;

    public Prefixmapping(final String prefix, final String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (prefix == null ? 0 : prefix.hashCode());
        result = prime * result + (uri == null ? 0 : uri.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Prefixmapping other = (Prefixmapping) obj;
        if (prefix == null) {
            if (other.prefix != null)
                return false;
        }
        else if (!prefix.equals(other.prefix))
            return false;
        if (uri == null) {
            if (other.uri != null)
                return false;
        }
        else if (!uri.equals(other.uri))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Prefixmapping [prefix=" + prefix + ", uri=" + uri + "]";
    }

}
