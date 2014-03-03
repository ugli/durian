package se.ugli.durian.j.dom.node;

import java.util.List;
import java.util.Set;

public interface Element extends Content {

    // TODO create interface Root

    String getName();

    String getUri();

    Document getDocument();

    Set<Attribute> getAttributes();

    List<Element> getElements();

    List<Element> getElements(String elementName);

    Element getElement(String elementName);

    List<Text> getTexts();

    boolean isSimpleTextNode();

    boolean isRoot();

    Attribute getAttribute(String attributeName);

    String getAttributeValue(String attributeName);

    @Deprecated
    // use getAttributes.add
    void setAttributeValue(String attributeName, String value);

    Element clone();

}
