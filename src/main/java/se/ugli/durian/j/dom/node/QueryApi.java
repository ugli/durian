package se.ugli.durian.j.dom.node;

import java.util.List;
import java.util.Optional;

public interface QueryApi {

    <T extends Attribute> Optional<T> attribute(String query);

    <T extends Attribute> List<T> attributes(String query);

    Optional<String> attributeValue(String query);

    <T extends Element> Optional<T> element(String query);

    <T extends Element> List<T> elements(String query);

    <T extends Node> Optional<T> node(String query);

    <T extends Node> List<T> nodes(String query);

    Optional<String> text(String query);

    List<String> texts(String query);

    boolean evaluteBoolean(String query);

}
