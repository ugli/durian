package se.ugli.durian.j.dom.node;

import java.util.List;

public interface QueryApi {

    <T extends Attribute> T attribute(String query);

    <T extends Attribute> List<T> attributes(String query);

    String attributeValue(String query);

    <T extends Element> T element(String query);

    <T extends Element> List<T> elements(String query);

    <T extends Node> T node(String query);

    <T extends Node> List<T> nodes(String query);

    String text(String query);

    List<String> texts(String query);

    boolean evaluteBoolean(String query);

}
