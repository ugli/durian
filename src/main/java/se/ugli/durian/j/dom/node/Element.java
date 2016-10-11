package se.ugli.durian.j.dom.node;

import java.util.List;

public interface Element extends Content {

    ElementCloner clone();

    <T extends Attribute> T getAttributeByName(String attributeName);

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

    String getUri();

    <T extends Attribute> T selectAttribute(String query);

    <T extends Attribute> T selectAttributeClone(String query);

    <T extends Attribute> T selectAttributeClone(String query, NodeFactory nodeFactory);

    <T extends Attribute> T selectAttributeClone(String query, NodeFactory nodeFactory, String attributeName);

    <T extends Attribute> List<T> selectAttributes(String query);

    String selectAttributeValue(String query);

    <T extends Element> T selectElement(String query);

    <T extends Element> T selectElementClone(String query);

    <T extends Element> T selectElementClone(String query, NodeFactory nodeFactory);

    <T extends Element> T selectElementClone(String query, NodeFactory nodeFactory, String elementName);

    <T extends Element> List<T> selectElements(String query);

    <T extends Node> T selectNode(String query);

    <T extends Node> List<T> selectNodes(String query);

    String selectText(String query);

    List<String> selectTexts(String query);

    boolean evaluteBoolean(String query);

    String qName();

    Iterable<PrefixMapping> prefixMappings();

    String toXml();

}
