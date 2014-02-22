package se.ugli.durian.j.dom.node;


public interface Content extends Node {

    Iterable<Content> getContent();

}
