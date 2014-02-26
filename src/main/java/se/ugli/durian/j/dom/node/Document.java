package se.ugli.durian.j.dom.node;

import java.util.Set;

public interface Document {

    Element getRoot();

    void setRoot(Element root);

    Set<String> getUriSet();

    void addPrefixMapping(String uri, String prefix);

    void removeUri(String uri);

    String getPrefix(String uri);
}
