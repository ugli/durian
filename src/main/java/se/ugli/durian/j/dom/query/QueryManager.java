package se.ugli.durian.j.dom.query;

import java.util.ArrayList;
import java.util.List;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;
import se.ugli.durian.j.dom.utils.AttributeCloneCommand;
import se.ugli.durian.j.dom.utils.TextCloneCommand;

public final class QueryManager {

    private QueryManager() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends Node> T selectNode(final Element element, final String query) {
        final List<? extends Node> nodes = selectNodes(element, query);
        if (nodes.isEmpty())
            return null;
        else if (nodes.size() == 1)
            return (T) nodes.get(0);
        throw new QueryException("Result contains " + nodes.size() + " nodes.");
    }

    public static <T extends Node> T selectNodeClone(final Element element, final String query, final NodeFactory nodeFactory,
            final String nodeName) {
        final Node node = selectNode(element, query);
        if (node != null)
            if (node instanceof Element) {
                final Element elementNode = (Element) node;
                return elementNode.cloneElement(nodeName, nodeFactory);
            }
            else if (node instanceof Attribute) {
                final Attribute attributeToClone = (Attribute) node;
                return AttributeCloneCommand.execute(attributeToClone, null, nodeFactory);
            }
            else if (node instanceof Text) {
                final Text textToClone = (Text) node;
                return TextCloneCommand.execute(textToClone, null, nodeFactory);
            }
            else
                throw new IllegalStateException();
        return null;
    }

    public static <T extends Node> T selectNodeClone(final Element element, final String query, final NodeFactory nodeFactory) {
        final Node node = selectNode(element, query);
        if (node != null)
            if (node instanceof Element) {
                final Element elementNode = (Element) node;
                return elementNode.cloneElement(nodeFactory);
            }
            else if (node instanceof Attribute) {
                final Attribute attributeToClone = (Attribute) node;
                return AttributeCloneCommand.execute(attributeToClone, null, nodeFactory);
            }
            else if (node instanceof Text) {
                final Text textToClone = (Text) node;
                return TextCloneCommand.execute(textToClone, null, nodeFactory);
            }
            else
                throw new IllegalStateException();
        return null;
    }

    public static <T extends Node> List<T> selectNodes(final Element element, final String query) {
        return QueryEngineFactory.create().selectNodes(element, query);
    }

    public static String selectText(final Element element, final String path) {
        final Text text = selectNode(element, path);
        if (text != null)
            return text.getValue();
        return null;
    }

    public static List<String> selectTexts(final Element element, final String path) {
        final List<Text> texts = selectNodes(element, path);
        final List<String> strings = new ArrayList<String>();
        for (final Text text : texts)
            strings.add(text.getValue());
        return strings;
    }

}
