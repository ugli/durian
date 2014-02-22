package se.ugli.durian.j.dom.node;


public interface Document {

    Element getRoot();

    void setRoot(Element root);

    Iterable<PrefixMapping> getPrefixMappings();

    void add(final PrefixMapping prefixMapping);

    void remove(final PrefixMapping prefixMapping);
}
