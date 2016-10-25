package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.QueryApi;

public interface MutableQueryApi extends QueryApi {

    int remove(final String query);

    int setAttributeValues(final String query, final String value);

}
