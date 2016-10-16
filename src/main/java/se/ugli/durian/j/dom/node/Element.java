package se.ugli.durian.j.dom.node;

import java.util.Optional;

public interface Element extends Content {

    ElementCloneApi clone();

    QueryApi select();

    <T extends Attribute> Optional<T> getAttributeByName(String attributeName);

    <T extends Attribute> Iterable<T> getAttributes();

    boolean hasAttributes();

    String getAttributeValue(String attributeName);

    Iterable<Content> getContent();

    <T extends Element> T getElementByName(String elementName);

    <T extends Element> Iterable<T> getElements();

    boolean hasElements();

    <T extends Element> Iterable<T> getElementsByName(String elementName);

    String getElementText(final String elementName);

    String getName();

    String getPath(String childPath);

    String getRelativePath(String childPath);

    <T extends Text> Iterable<T> getTexts();

    boolean hasTexts();

    boolean hasNodes();

    Optional<String> getUri();

    String qName();

    Iterable<PrefixMapping> prefixMappings();

    String toXml();

}
