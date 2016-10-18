package se.ugli.durian.j.dom.node;

import java.util.Optional;
import java.util.stream.Stream;

public interface QueryApi {

    Optional<Attribute> attribute(String query);

    Stream<Attribute> attributes(String query);

    Optional<String> attributeValue(String query);

    Optional<Element> element(String query);

    Stream<Element> elements(String query);

    boolean evaluteBoolean(String query);

    Optional<Node> node(String query);

    Stream<Node> nodes(String query);

    Optional<String> text(String query);

    Stream<String> texts(String query);

}
