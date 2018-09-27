package se.ugli.durian.j.dom.query;

import java.util.Optional;
import java.util.stream.Stream;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.Text;

public final class QueryManager {

	private QueryManager() {
	}

	public static Optional<Node> selectNode(final Element element, final String query) {
		return selectNodes(element, query).findFirst();
	}

	public static Stream<Node> selectNodes(final Element element, final String query) {
		return QueryEngineFactory.create().selectNodes(element, query);
	}

	public static Optional<String> selectText(final Element element, final String path) {
		return selectNode(element, path).map(n -> n.as(Text.class)).map(Text::value);
	}

	public static Stream<String> selectTexts(final Element element, final String path) {
		return selectNodes(element, path).map(n -> n.as(Text.class)).map(Text::value);
	}

	public static boolean evaluteBoolean(final Element element, final String query) {
		return QueryEngineFactory.create().evalateBoolean(element, query);
	}

	public static long evaluteLong(final Element element, final String query) {
		return QueryEngineFactory.create().evaluteLong(element, query);
	}

	public static double evaluteDouble(final Element element, final String query) {
		return QueryEngineFactory.create().evaluteDouble(element, query);
	}

}