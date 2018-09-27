package se.ugli.durian.j.dom.node;

import java.util.Optional;
import java.util.stream.Stream;

public interface QueryApi {

	Optional<Attribute> attribute(String query);

	Stream<Attribute> attributes(String query);

	Optional<String> attributeValue(String query);

	Stream<String> attributeValues(String query);

	boolean boolValue(String query);

	double doubleValue(String query);

	Optional<Element> element(String query);

	Stream<Element> elements(String query);

	long longValue(String query);

	Optional<Node> node(String query);

	Stream<Node> nodes(String query);

	Optional<String> text(String query);

	Stream<String> texts(String query);

}
