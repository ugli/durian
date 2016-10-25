package se.ugli.durian.j.dom.mutable;

import java.util.Optional;
import java.util.stream.Stream;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.query.QueryManager;

public class MutableQueryApiImpl implements MutableQueryApi {

    private final MutableElement mutableElement;

    public MutableQueryApiImpl(final MutableElement mutableElement) {
        this.mutableElement = mutableElement;
    }

    @Override
    public Optional<Attribute> attribute(final String query) {
        return QueryManager.selectNode(mutableElement, query).map(n -> n.as(Attribute.class));
    }

    @Override
    public Stream<Attribute> attributes(final String query) {
        return QueryManager.selectNodes(mutableElement, query).map(n -> n.as(Attribute.class));
    }

    @Override
    public Optional<String> attributeValue(final String query) {
        return QueryManager.selectNode(mutableElement, query).map(n -> n.as(Attribute.class)).map(Attribute::value);
    }

    @Override
    public Optional<Element> element(final String query) {
        return QueryManager.selectNode(mutableElement, query).map(n -> n.as(Element.class));
    }

    @Override
    public Stream<Element> elements(final String query) {
        return QueryManager.selectNodes(mutableElement, query).map(n -> n.as(Element.class));
    }

    @Override
    public boolean boolValue(final String query) {
        return QueryManager.evaluteBoolean(mutableElement, query);
    }

    @Override
    public Optional<Node> node(final String query) {
        return QueryManager.selectNode(mutableElement, query);
    }

    @Override
    public Stream<Node> nodes(final String query) {
        return QueryManager.selectNodes(mutableElement, query);
    }

    @Override
    public Optional<String> text(final String query) {
        return QueryManager.selectText(mutableElement, query);
    }

    @Override
    public Stream<String> texts(final String query) {
        return QueryManager.selectTexts(mutableElement, query);
    }

    // Mutable
    @Override
    public int setAttributeValues(final String query, final String value) {
        return (int) attributes(query).map(a -> a.as(MutableAttribute.class)).peek(a -> a.setValue(value)).count();
    }

    @Override
    public int remove(final String query) {
        return (int) nodes(query).filter(n -> n.parent().isPresent()).filter(n -> n.parent().get() instanceof MutableElement).peek(n -> {
            n.parent().get().as(MutableElement.class).remove(n);
        }).count();
    }

    @Override
    public long longValue(final String query) {
        return QueryManager.evaluteLong(mutableElement, query);
    }

    @Override
    public double doubleValue(final String query) {
        return QueryManager.evaluteDouble(mutableElement, query);
    }

    @Override
    public Stream<String> attributeValues(final String query) {
        return attributes(query).map(Attribute::value);
    }

}
