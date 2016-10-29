package se.ugli.durian.w3c.dom;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;

public class DocumentReader {

    private final NodeFactory nodeFactory;

    public DocumentReader(final NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public Element read(final Document document) {
        return read(document.getDocumentElement());
    }

    private Element read(final org.w3c.dom.Element element) {
        final String name = element.getNodeName();
        final String uri = element.getNamespaceURI();
        final Element parent = null;
        final Iterable<PrefixMapping> prefixMappings = new ArrayList<>();
        final MutableElement newElement = nodeFactory.createElement(name, uri, parent, prefixMappings)
                .as(MutableElement.class);
        append(newElement, element);
        return newElement;
    }

    private void append(final MutableElement newElement, final org.w3c.dom.Element element) {
        append(newElement, element.getAttributes());
        append(newElement, element.getChildNodes());
    }

    private void append(final MutableElement newElement, final NodeList childNodes) {
        for (int i = 0; i < childNodes.getLength(); i++)
            appendNode(childNodes.item(i), newElement);
    }

    private void append(final MutableElement newElement, final NamedNodeMap attributes) {
        for (int i = 0; i < attributes.getLength(); i++)
            appendNode(attributes.item(i), newElement);
    }

    private void appendNode(final Node w3cNode, final MutableElement parent) {
        final String name = w3cNode.getNodeName();
        final String uri = w3cNode.getBaseURI();
        final String value = w3cNode.getNodeValue();
        switch (w3cNode.getNodeType()) {
        case Node.ELEMENT_NODE:
            final MutableElement newElement = parent.addElement(name, uri, nodeFactory).as(MutableElement.class);
            append(newElement, (org.w3c.dom.Element) w3cNode);
            break;
        case Node.ATTRIBUTE_NODE:
            if (name.startsWith("xmlns"))
                addPrefixMapping(parent, name, value);
            else
                parent.addAttribute(name, uri, value, nodeFactory);
            break;
        case Node.TEXT_NODE:
            if (!value.trim().isEmpty())
                parent.addText(("X" + value.trim()).substring(1));
            break;
        default:
            // Ignore
        }
    }

    private static void addPrefixMapping(final MutableElement parent, final String attributeName, final String uri) {
        if ("xmlns".equals(attributeName))
            parent.addPrefixMapping(null, uri);
        else
            parent.addPrefixMapping(attributeName.replace("xmlns:", ""), uri);

    }

}
