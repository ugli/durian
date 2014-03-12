package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;

public class MutableAttribute implements Attribute {

    private String value;
    private final Element parent;
    private final String name;
    private final String uri;

    public MutableAttribute(final String name, final String uri, final Element parent, final String value) {
        this.name = name;
        this.uri = uri;
        this.parent = parent;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Element getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        return parent.getPath() + "/@" + name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MutableAttribute other = (MutableAttribute) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public void setValue(final String value) {
        this.value = value;
    }

}
