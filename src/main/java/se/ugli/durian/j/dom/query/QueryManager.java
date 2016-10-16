package se.ugli.durian.j.dom.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.Text;

public final class QueryManager {

    private QueryManager() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends Node> Optional<T> selectNode(final Element element, final String query) {
        final List<? extends Node> nodes = selectNodes(element, query);
        if (nodes.isEmpty())
            return Optional.empty();
        else if (nodes.size() == 1)
            return (Optional<T>) Optional.of(nodes.get(0));
        throw new QueryException("Result contains " + nodes.size() + " nodes.");
    }

    public static <T extends Node> List<T> selectNodes(final Element element, final String query) {
        return QueryEngineFactory.create().selectNodes(element, query);
    }

    public static Optional<String> selectText(final Element element, final String path) {
        return selectNode(element, path).map(n -> (Text) n).map(Text::getValue);
    }

    public static List<String> selectTexts(final Element element, final String path) {
        final List<Text> texts = selectNodes(element, path);
        final List<String> strings = new ArrayList<String>();
        for (final Text text : texts)
            strings.add(text.getValue());
        return strings;
    }

    public static boolean evaluteBoolean(final Element element, final String query) {
        return QueryEngineFactory.create().evalateBoolean(element, query);
    }

}