package se.ugli.durian.j.dom.query;

import se.ugli.durian.j.dom.query.simple.SimpleQueryEngine;

public class QueryEngineFactory {

    private QueryEngineFactory() {
    }

    public static QueryEngine create() {
        return new SimpleQueryEngine();
    }
}
