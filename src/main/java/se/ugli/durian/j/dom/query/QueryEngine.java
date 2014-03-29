package se.ugli.durian.j.dom.query;

import java.util.List;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;

public interface QueryEngine {

    public final static String SPI_RESOURCE_PATH = "/META-INF/services/se.ugli.durian.j.dom.query.engine";

    <T extends Node> List<T> selectNodes(Element element, String query) throws QueryException;

}
