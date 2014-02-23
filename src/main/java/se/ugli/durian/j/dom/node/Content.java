package se.ugli.durian.j.dom.node;

import java.util.List;

public interface Content extends Node {

    List<Content> getContent();

}
