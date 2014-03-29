package se.ugli.durian.j.dom.node;

import java.util.List;
import java.util.Set;

public interface Element extends Content {

    <T extends Element> T cloneElement();

    <T extends Element> T cloneElement(NodeFactory nodeFactory);

    <T extends Element> T cloneElement(String elementName);

    <T extends Element> T cloneElement(String elementName, NodeFactory nodeFactory);

    <T extends Attribute> T getAttribute(String attributeName);

    <T extends Attribute> Set<T> getAttributes();

    String getAttributeValue(String attributeName);

    <T extends Element> T getElement(String elementName);

    <T extends Element> List<T> getElements();

    <T extends Element> List<T> getElements(String elementName);

    String getElementText(final String elementName);

    String getName();

    String getPath(String childPath);

    String getRelativePath(String childPath);

    <T extends Text> List<T> getTexts();

    String getUri();

    Set<String> getUriSet();

    boolean isSimpleTextNode();

    <T extends Attribute> T selectAttribute(String query);

    <T extends Attribute> List<T> selectAttributes(String query);

    <T extends Element> T selectElement(String query);

    <T extends Element> List<T> selectElements(final String query);

    <T extends Node> T selectNode(String query);

    <T extends Node> List<T> selectNodes(String query);

    String selectText(String query);

    List<String> selectTexts(String query);

}
