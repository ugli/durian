package se.ugli.durian.j.dom.mutable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;

public class MutableDocument implements Document {

    private Element root;
    private final Map<String, String> uriMap = new LinkedHashMap<String, String>();

    @Override
    public Element getRoot() {
        return root;
    }

    @Override
    public void setRoot(final Element root) {
        this.root = root;
    }

    @Override
    public String getPrefix(final String uri) {
        return uriMap.get(uri);
    }

    @Override
    public void addPrefixMapping(final String uri, final String prefix) {
        uriMap.put(uri, prefix);
    }

    @Override
    public void removeUri(final String uri) {
        uriMap.remove(uri);
    }

    @Override
    public Set<String> getUriSet() {
        return uriMap.keySet();
    }

}
