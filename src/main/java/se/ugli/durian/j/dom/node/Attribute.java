package se.ugli.durian.j.dom.node;

public interface Attribute extends Node {

    String getName();

    String getUri();

    String getQName();

    String getValue();

}
