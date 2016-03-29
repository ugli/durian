package se.ugli.durian.j.dom.query;

import java.util.List;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;

public interface QueryEngine {

    <T extends Node> List<T> selectNodes(Element element, String query) throws QueryException;

    boolean evalateBoolean(Element element, String query) throws QueryException;

}