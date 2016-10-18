package se.ugli.durian.j.dom.mutable;

import static java.util.stream.Collectors.toList;

import java.util.List;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.ElementCloneApi;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

class MutableElementCloneApiImpl implements ElementCloneApi {

    private static List<Attribute> cloneAttributes(final Element elementToClone, final Element elementClone,
            final NodeFactory nodeFactory) {
        return elementToClone.attributes().map(a -> a.clone().attribute(elementClone, nodeFactory)).collect(toList());
    }

    private static Content cloneChildElement(final Element elementToClone, final Element parent, final NodeFactory nodeFactory) {
        final String elementName = elementToClone.name();
        return cloneElement(elementName, elementToClone, parent, nodeFactory);
    }

    private static Content cloneContent(final Content contentToClone, final Element parent, final NodeFactory nodeFactory) {
        if (contentToClone instanceof Text) {
            final Text textToClone = contentToClone.as(Text.class);
            return textToClone.clone().text(parent, nodeFactory);
        }
        else if (contentToClone instanceof Element)
            return cloneChildElement(contentToClone.as(Element.class), parent, nodeFactory);
        throw new IllegalStateException();
    }

    private static List<Content> cloneContentList(final Element elementToClone, final Element elementClone, final NodeFactory nodeFactory) {
        return elementToClone.content().map(c -> cloneContent(c, elementClone, nodeFactory)).collect(toList());
    }

    private static Element cloneElement(final String elementName, final Element elementToClone, final Element parent,
            final NodeFactory nodeFactory) {
        final MutableElement elementClone = createElement(elementName, elementToClone, parent, nodeFactory).as(MutableElement.class);
        for (final Attribute a : cloneAttributes(elementToClone, elementClone, nodeFactory))
            elementClone.add(a);
        for (final Content c : cloneContentList(elementToClone, elementClone, nodeFactory))
            elementClone.add(c);
        return elementClone;
    }

    private static Element cloneElement(final String elementName, final Element elementToClone, final NodeFactory nodeFactory) {
        return cloneElement(elementName, elementToClone, null, nodeFactory);
    }

    private static Element createElement(final String elementName, final Element elementToClone, final Element parent,
            final NodeFactory nodeFactory) {
        return nodeFactory.createElement(elementName, elementToClone.uri().orElse(null), parent,
                elementToClone.prefixMappings().collect(toList()));
    }

    private final MutableElement element;

    private final NodeFactory nodeFactory;

    private final String elementName;

    public MutableElementCloneApiImpl(final MutableElement element) {
        this.element = element;
        this.nodeFactory = element.nodeFactory();
        this.elementName = element.name();
    }

    @Override
    public Element element() {
        return cloneElement(elementName, element, nodeFactory);
    }

    @Override
    public Element element(final NodeFactory nodeFactory) {
        return cloneElement(elementName, element, nodeFactory);
    }

    @Override
    public Element element(final String elementName) {
        return cloneElement(elementName, element, nodeFactory);
    }

    @Override
    public Element element(final String elementName, final NodeFactory nodeFactory) {
        return cloneElement(elementName, element, nodeFactory);
    }

}
