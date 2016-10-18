package se.ugli.durian.j.dom.query;

import java.util.stream.Stream;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;

public interface QueryEngine {

    Stream<Node> selectNodes(Element element, String query) throws QueryException;

    boolean evalateBoolean(Element element, String query) throws QueryException;

    long evaluteLong(Element element, String query);

    double evaluteDouble(Element element, String query);

}