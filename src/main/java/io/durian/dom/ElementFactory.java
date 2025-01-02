package io.durian.dom;

import io.durian.Attribute;
import io.durian.Content;
import io.durian.Element;
import io.durian.immutable.ImmutableAttribute;
import io.durian.immutable.ImmutableElement;
import io.durian.immutable.ImmutableText;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static org.w3c.dom.Node.ELEMENT_NODE;
import static org.w3c.dom.Node.TEXT_NODE;

public class ElementFactory {

    public static Element create(Document document) {
        return createElement(document.getDocumentElement(), null);
    }

    static Element createElement(org.w3c.dom.Element domElement, Element parent) {
        List<Attribute> attributes = new ArrayList<>();
        List<Content> content = new ArrayList<>();
        Element element = new ImmutableElement(
                randomUUID().toString(),
                domElement.getNodeName(),
                content,
                attributes,
                ofNullable(parent),
                empty()
        );
        attributes.addAll(createAttributes(domElement.getAttributes(), element));
        content.addAll(createContent(domElement.getChildNodes(), element));
        return element;
    }

    static List<Content> createContent(NodeList childNodes, Element parent) {
        List<Content> result = new ArrayList<>();
        for (int index = 0; index < childNodes.getLength(); index++) {
            Node node = childNodes.item(index);
            switch (node.getNodeType()) {
                case ELEMENT_NODE -> result.add(createElement(
                                (org.w3c.dom.Element) node,
                                parent
                        )
                );
                case TEXT_NODE -> {
                    String text = node.getNodeValue();
                    if (!text.trim().isBlank())
                        result.add(new ImmutableText(
                                        randomUUID().toString(),
                                        text,
                                        of(parent)
                                )
                        );
                }
            }
        }
        return result;
    }

    static List<Attribute> createAttributes(NamedNodeMap namedNodeMap, Element parent) {
        List<Attribute> result = new ArrayList<>();
        for (int index = 0; index < namedNodeMap.getLength(); index++) {
            Node node = namedNodeMap.item(index);
            result.add(new ImmutableAttribute(
                            randomUUID().toString(),
                            node.getNodeName(),
                            node.getNodeValue(),
                            of(parent),
                            empty()
                    )
            );
        }
        return result;
    }

}