package se.ugli.durian.j.dom.mutable;

import java.util.List;

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
    public <T extends Attribute> T attribute(final String query) {
        return QueryManager.selectNode(mutableElement, query);
    }

    @Override
    public <T extends Attribute> List<T> attributes(final String query) {
        return QueryManager.selectNodes(mutableElement, query);
    }

    @Override
    public String attributeValue(final String query) {
        final Attribute attribute = QueryManager.selectNode(mutableElement, query);
        if (attribute != null)
            return attribute.getValue();
        return null;
    }

    @Override
    public <T extends Element> T element(final String query) {
        return QueryManager.selectNode(mutableElement, query);
    }

    @Override
    public <T extends Element> List<T> elements(final String query) {
        return QueryManager.selectNodes(mutableElement, query);
    }

    @Override
    public <T extends Node> T node(final String query) {
        return QueryManager.selectNode(mutableElement, query);
    }

    @Override
    public <T extends Node> List<T> nodes(final String query) {
        return QueryManager.selectNodes(mutableElement, query);
    }

    @Override
    public String text(final String query) {
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
        final MutableAttribute attribute = attribute(query);
        if (attribute != null)
            attribute.setValue(value);
        return attribute != null;
    }

}
