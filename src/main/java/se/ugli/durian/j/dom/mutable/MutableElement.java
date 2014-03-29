package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public interface MutableElement extends Element {

    <T extends Attribute> T addAttribute(String name, String value);

    <T extends Attribute> T addAttribute(String name, String value, NodeFactory nodeFactory);

    <T extends Attribute> T addAttribute(String name, String uri, String value);

    <T extends Attribute> T addAttribute(String name, String uri, String value, NodeFactory nodeFactory);

    <T extends Element> T addElement(String name);

    <T extends Element> T addElement(String name, NodeFactory nodeFactory);

    <T extends Element> T addElement(String name, String uri);

    <T extends Element> T addElement(String name, String uri, NodeFactory nodeFactory);

    <T extends Text> T addText(String value);

    <T extends Text> T addText(String value, NodeFactory nodeFactory);

    void setAttributeValue(String attributeName, String value);

    void setParent(final MutableElement parent);

}
