package se.ugli.durian.j.dom.query.simple;

public class Path {

    final String value;

    Path(final String value) {
        this.value = value.trim();
    }

    boolean isRelative() {
        return !value.startsWith("/");
    }

    boolean isAbsolute() {
        return value.startsWith("/") && !value.startsWith("//");
    }

    boolean isDescendantOrSelf() {
        return value.startsWith("//");
    }

    @Override
    public String toString() {
        return "Path [value=" + value + "]";
    }

    String superAsRelative() {
        return value.substring(2);
    }

}
