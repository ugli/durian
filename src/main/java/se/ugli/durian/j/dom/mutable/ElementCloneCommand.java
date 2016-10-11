package se.ugli.durian.j.dom.mutable;

import java.util.ArrayList;
import java.util.List;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

final class ElementCloneCommand {

    private ElementCloneCommand() {

    }

    public static <T extends Element> T cloneElement(final String elementName, final Element elementToClone,
            final NodeFactory nodeFactory) {
        return cloneElement(elementName, elementToClone, null, nodeFactory);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Element> T cloneElement(final String elementName, final Element elementToClone, final Element parent,
            final NodeFactory nodeFactory) {
        final MutableElement elementClone = createElement(elementName, elementToClone, parent, nodeFactory);
        for (final Attribute a : cloneAttributes(elementToClone, elementClone, nodeFactory))
            elementClone.add(a);
        for (final Content c : cloneContentList(elementToClone, elementClone, nodeFactory))
            elementClone.add(c);
        return (T) elementClone;
    }

    private static MutableElement createElement(final String elementName, final Element elementToClone, final Element parent,
            final NodeFactory nodeFactory) {
        return nodeFactory.createElement(elementName, elementToClone.getUri(), parent, elementToClone.prefixmappings());
    }

    private static List<Attribute> cloneAttributes(final Element elementToClone, final Element elementClone,
            final NodeFactory nodeFactory) {
        final List<Attribute> result = new ArrayList<Attribute>();
        for (final Attribute attributeToClone : elementToClone.getAttributes())
            result.add(attributeToClone.clone().attribute(elementClone, nodeFactory));
        return result;
    }

    private static List<Content> cloneContentList(final Element elementToClone, final Element elementClone, final NodeFactory nodeFactory) {
        final List<Content> result = new ArrayList<Content>();
        for (final Content contentToClone : elementToClone.getContent())
            result.add(cloneContent(contentToClone, elementClone, nodeFactory));
        return result;
    }

    private static Content cloneContent(final Content contentToClone, final Element parent, final NodeFactory nodeFactory) {
        if (contentToClone instanceof Text) {
            final Text textToClone = (Text) contentToClone;
            return textToClone.clone().text(parent, nodeFactory);
        }
        else if (contentToClone instanceof Element)
            return cloneChildElement((Element) contentToClone, parent, nodeFactory);
        throw new IllegalStateException();
    }

    private static Content cloneChildElement(final Element elementToClone, final Element parent, final NodeFactory nodeFactory) {
        final String elementName = elementToClone.getName();
        return cloneElement(elementName, elementToClone, parent, nodeFactory);
    }

}
