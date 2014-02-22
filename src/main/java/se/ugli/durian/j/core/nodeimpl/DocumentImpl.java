package se.ugli.durian.j.core.nodeimpl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import se.ugli.durian.j.core.Document;
import se.ugli.durian.j.core.Element;
import se.ugli.durian.j.core.PrefixMapping;

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
    public Collection<? extends PrefixMapping> getPrefixMappings() {
	return uriMap.values();
    }

    @Override
    public void add(final PrefixMapping prefixMapping) {
	uriMap.put(prefixMapping.getUri(), prefixMapping);
    }

}
