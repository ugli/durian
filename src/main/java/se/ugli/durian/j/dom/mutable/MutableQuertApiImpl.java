package se.ugli.durian.j.dom.mutable;

import java.util.Optional;
import java.util.stream.Stream;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.query.QueryManager;

public class MutableQuertApiImpl implements MutableQuertApi {

    private final MutableElement mutableElement;

    public MutableQuertApiImpl(final MutableElement mutableElement) {
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
    public boolean evaluteBoolean(final String query) {
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
    public int removeByQuery(final String query) {
        return mutableElement.removeAll(nodes(query));
    }

    @Override
    public boolean setAttributeValueByQuery(final String query, final String value) {
        final Optional<MutableAttribute> attributeOpt = attribute(query).map(a -> a.as(MutableAttribute.class));
        if (attributeOpt.isPresent())
            attributeOpt.get().setValue(value);
        return attributeOpt.isPresent();
    }

    // Mutable

    @Override
    public Optional<String> text(final String query) {
        return QueryManager.selectText(mutableElement, query);
    }

    @Override
    public Stream<String> texts(final String query) {
        return QueryManager.selectTexts(mutableElement, query);
    }

}
