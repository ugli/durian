package se.ugli.durian.j.dom.node;

import java.util.Optional;

public interface Element extends Content {

    ElementCloneApi clone();

    QueryApi select();

    Optional<Attribute> getAttributeByName(String attributeName);

    Iterable<Attribute> getAttributes();

    boolean hasAttributes();

    Optional<String> getAttributeValue(String attributeName);

    Iterable<Content> getContent();

    Optional<Element> getElementByName(String elementName);

    Iterable<Element> getElements();

    boolean hasElements();

    Iterable<Element> getElementsByName(String elementName);

    Optional<String> getElementText(final String elementName);

    String getName();

    String getPath(String childPath);

    String getRelativePath(String childPath);

    Iterable<Text> getTexts();

    boolean hasTexts();

    boolean hasNodes();

    Optional<String> getUri();

    String qName();

    Iterable<PrefixMapping> prefixMappings();

    String toXml();

}
