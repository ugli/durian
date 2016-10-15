package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.QueryApi;

public interface MutableQuertApi extends QueryApi {

    int removeByQuery(final String query);

    boolean setAttributeValueByQuery(final String query, final String value);

}
