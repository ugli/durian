package se.ugli.durian.j.dom.query;

import java.util.List;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;

public interface QueryEngine {

    <T extends Node> T selectNode(Element element, String path);

    List<? extends Node> selectNodes(Element element, String path);

}
