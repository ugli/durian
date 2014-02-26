package se.ugli.durian.j.dom.node;

import java.util.Iterator;

public interface Document {

    Element getRoot();

    void setRoot(Element root);

    Iterator<PrefixMapping> getPrefixMappings();

    void add(final PrefixMapping prefixMapping);

    void remove(final PrefixMapping prefixMapping);

    String getPrefix(String uri);
}
