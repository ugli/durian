package se.ugli.durian.j.dom.node;

import java.util.Optional;
import java.util.stream.Stream;

public interface Element extends Content {

    Optional<Attribute> attribute(String attributeName);

    Stream<Attribute> attributes();

    Optional<String> attributeValue(String attributeName);

    ElementCloneApi clone();

    Stream<Content> content();

    Optional<Element> element(String elementName);

    Stream<Element> elements();

    Stream<Element> elements(String elementName);

    Optional<String> elementText(final String elementName);

    boolean hasAttributes();

    boolean hasElements();

    boolean hasNodes();

    boolean hasTexts();

    String name();

    String path(String childPath);

    Stream<PrefixMapping> prefixMappings();

    String qName();

    String relativePath(String childPath);

    QueryApi select();

    Stream<Text> texts();

    String toXml();

    Optional<String> uri();

}
