package se.ugli.durian.j.dom.node;

import java.util.Iterator;
import java.util.List;

public interface Element extends Content {

    Name getName();

    Document getDocument();

    List<Attribute> getAttributes();

    Iterator<Element> getElements();

    Iterator<Text> getTexts();

    String getText();

    boolean isSimpleTextNode();

    boolean isRoot();

}
