package se.ugli.durian.j.dom.query;

import se.ugli.durian.j.dom.query.jaxen.JaxenQueryEngine;

public class QueryEngineFactory {

    private static QueryEngine queryEngine;

    private QueryEngineFactory() {
    }

    static QueryEngine create() {
        if (queryEngine == null)
            queryEngine = new JaxenQueryEngine();
        return queryEngine;
    }

}
