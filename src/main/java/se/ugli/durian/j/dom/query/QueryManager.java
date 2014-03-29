package se.ugli.durian.j.dom.query;

import java.util.List;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;

public final class QueryManager {

    private QueryManager() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends Node> T selectNode(final Element element, final String query) {
        final List<? extends Node> nodes = selectNodes(element, query);
        if (nodes.isEmpty()) {
            return null;
        }
        else if (nodes.size() == 1) {
            return (T) nodes.get(0);
        }
        throw new IllegalStateException("Size: " + nodes.size());
    }

    public static <T extends Node> List<T> selectNodes(final Element element, final String query) {
        return QueryEngineFactory.create().selectNodes(element, query);
    }

}
