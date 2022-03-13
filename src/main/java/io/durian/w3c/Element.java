package io.durian.w3c;

import io.durian.dom.Content;
import io.durian.dom.Namespace;
import io.durian.immutable.Attribute;
import io.durian.immutable.Text;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static org.w3c.dom.Node.ELEMENT_NODE;
import static org.w3c.dom.Node.TEXT_NODE;

public class Element implements io.durian.dom.Element {
    private final String id = randomUUID().toString();
    private final String name;
    private final List<Content> content;
    private final List<Attribute> attributes;
    private final Element parent;

    public Element(Document document) {
        this(document.getDocumentElement(), null);
    }

    public Element(org.w3c.dom.Element element, Element parent) {
        this.name = element.getNodeName();
        this.parent = parent;
        this.content = mapContent(element.getChildNodes());
        this.attributes = mapAttributes(element.getAttributes());
    }

    private List<Content> mapContent(NodeList childNodes) {
        List<Content> result = new ArrayList<>();
        for (int index = 0; index < childNodes.getLength(); index++) {
            Node node = childNodes.item(index);
            switch (node.getNodeType()) {
                case ELEMENT_NODE -> result.add(
                        new Element((org.w3c.dom.Element) node, this)
                );
                case TEXT_NODE -> {
                    String text = node.getNodeValue();
                    if (!text.trim().isBlank())
                        result.add(new Text(text, this));
                }
            }
        }
        return result.stream().toList();
    }

    private List<Attribute> mapAttributes(NamedNodeMap namedNodeMap) {
        List<Attribute> result = new ArrayList<>();
        for (int index = 0; index < namedNodeMap.getLength(); index++) {
            Node node = namedNodeMap.item(index);
            result.add(new Attribute(node.getNodeName(), node.getNodeValue(), this, null));
        }
        return result.stream().toList();
    }

    @Override
    public List<Content> content() {
        return content;
    }

    @Override
    public List<Attribute> attributes() {
        return attributes;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Optional<Namespace> namespace() {
        return empty();
    }


    @Override
    public String id() {
        return id;
    }

    @Override
    public Optional<Element> parent() {
        return ofNullable(parent);
    }

}
