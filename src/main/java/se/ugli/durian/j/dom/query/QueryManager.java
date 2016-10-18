package se.ugli.durian.j.dom.query;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.Text;

public final class QueryManager {

    private QueryManager() {
    }

    public static Optional<Node> selectNode(final Element element, final String query) {
        final List<Node> nodes = selectNodes(element, query);
        if (nodes.isEmpty())
            return Optional.empty();
        else if (nodes.size() == 1)
            return Optional.of(nodes.get(0));
        throw new QueryException("Result contains " + nodes.size() + " nodes.");
    }

    public static List<Node> selectNodes(final Element element, final String query) {
        return QueryEngineFactory.create().selectNodes(element, query);
    }

    public static Optional<String> selectText(final Element element, final String path) {
        return selectNode(element, path).map(n -> n.as(Text.class)).map(Text::getValue);
    }

    public static List<String> selectTexts(final Element element, final String path) {
        return selectNodes(element, path).stream().map(n -> n.as(Text.class)).map(Text::getValue).collect(toList());
    }

    public static boolean evaluteBoolean(final Element element, final String query) {
        return QueryEngineFactory.create().evalateBoolean(element, query);
    }

}