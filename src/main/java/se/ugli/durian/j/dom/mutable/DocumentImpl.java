package se.ugli.durian.j.dom.mutable;

import java.util.LinkedHashMap;
import java.util.Map;

import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.PrefixMapping;

public class DocumentImpl implements Document {

    private Element root;
    private final Map<String, PrefixMapping> uriMap = new LinkedHashMap<String, PrefixMapping>();

    @Override
    public Element getRoot() {
        return root;
    }

    @Override
    public void setRoot(final Element root) {
        this.root = root;
    }

    @Override
    public Iterable<PrefixMapping> getPrefixMappings() {
        return uriMap.values();
    }

    @Override
    public void add(final PrefixMapping prefixMapping) {
        uriMap.put(prefixMapping.getUri(), prefixMapping);
    }

    @Override
    public void remove(final PrefixMapping prefixMapping) {
        uriMap.remove(prefixMapping.getUri());
    }

}
