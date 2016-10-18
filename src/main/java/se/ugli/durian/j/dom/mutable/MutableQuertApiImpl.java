package se.ugli.durian.j.dom.mutable;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

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
    public List<Attribute> attributes(final String query) {
        return QueryManager.selectNodes(mutableElement, query).stream().map(n -> n.as(Attribute.class)).collect(toList());
    }

    @Override
    public Optional<String> attributeValue(final String query) {
        return QueryManager.selectNode(mutableElement, query).map(n -> n.as(Attribute.class)).map(Attribute::getValue);
    }

    @Override
    public Optional<Element> element(final String query) {
        return QueryManager.selectNode(mutableElement, query).map(n -> n.as(Element.class));
    }

    @Override
    public List<Element> elements(final String query) {
        return QueryManager.selectNodes(mutableElement, query).stream().map(n -> n.as(Element.class)).collect(toList());
    }

    @Override
    public Optional<Node> node(final String query) {
        return QueryManager.selectNode(mutableElement, query);
    }

    @Override
    public List<Node> nodes(final String query) {
        return QueryManager.selectNodes(mutableElement, query);
    }

    @Override
    public Optional<String> text(final String query) {
        return QueryManager.selectText(mutableElement, query);
    }

    @Override
    public List<String> texts(final String query) {
        return QueryManager.selectTexts(mutableElement, query);
    }

    @Override
    public boolean evaluteBoolean(final String query) {
        return QueryManager.evaluteBoolean(mutableElement, query);
    }

    // Mutable

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

}
