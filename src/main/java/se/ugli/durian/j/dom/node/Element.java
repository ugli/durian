package se.ugli.durian.j.dom.node;

import java.util.List;

public interface Element extends Content {

    String getName();

    String getUri();

    Document getDocument();

    List<Attribute> getAttributes();

    List<Element> getElements();

    List<Text> getTexts();

    boolean isSimpleTextNode();

    boolean isRoot();

}
