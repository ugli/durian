package se.ugli.durian.j.core;

import java.util.Collection;

public interface Document extends Node {

    Element getRoot();

    Collection<? extends PrefixMapping> getPrefixMappings();

    void setRoot(Element root);

    void add(final PrefixMapping prefixMapping);
}
