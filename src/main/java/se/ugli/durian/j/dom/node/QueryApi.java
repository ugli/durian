package se.ugli.durian.j.dom.node;

import java.util.List;
import java.util.Optional;

public interface QueryApi {

    Optional<Attribute> attribute(String query);

    List<Attribute> attributes(String query);

    Optional<String> attributeValue(String query);

    Optional<Element> element(String query);

    List<Element> elements(String query);

    Optional<Node> node(String query);

    List<Node> nodes(String query);

    Optional<String> text(String query);

    List<String> texts(String query);

    boolean evaluteBoolean(String query);

}
