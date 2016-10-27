package se.ugli.durian.j.dom.query.jaxen;

import java.util.stream.Stream;

import org.jaxen.JaxenException;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.query.QueryEngine;
import se.ugli.durian.j.dom.query.QueryException;

public class JaxenQueryEngine implements QueryEngine {

    @SuppressWarnings("unchecked")
    @Override
    public Stream<Node> selectNodes(final Element element, final String query) {
        try {
            final DurianXpath durianXpath = new DurianXpath(element, query);
            return durianXpath.selectNodes(element).stream();
        }
        catch (final JaxenException e) {
            throw new QueryException(e);
        }
    }

    @Override
    public boolean evalateBoolean(final Element element, final String query) {
        try {
            final DurianXpath durianXpath = new DurianXpath(element, query);
            final Object result = durianXpath.selectSingleNode(element);
            if (result == null)
                throw new QueryException("Query doesn't evalate a Boolean. Result is null. Query: " + query);
            else if (!(result instanceof Boolean))
                throw new QueryException("Query doesn't evalate a Boolean. Result has type: "
                        + result.getClass().getName() + " Query: " + query);
            return (Boolean) result;

        }
        catch (final JaxenException e) {
            throw new QueryException(e);
        }
    }

    @Override
    public long evaluteLong(final Element element, final String query) {
        return _evaluteDouble(element, query).longValue();
    }

    @Override
    public double evaluteDouble(final Element element, final String query) {
        return _evaluteDouble(element, query);
    }

    private Double _evaluteDouble(final Element element, final String query) {
        try {
            final DurianXpath durianXpath = new DurianXpath(element, query);
            final Object result = durianXpath.selectSingleNode(element);
            if (result == null)
                throw new QueryException("Query doesn't evalate a Double. Result is null. Query: " + query);
            else if (!(result instanceof Double))
                throw new QueryException("Query doesn't evalate a Double. Result has type: "
                        + result.getClass().getName() + " Query: " + query);
            return (Double) result;

        }
        catch (final JaxenException e) {
            throw new QueryException(e);
        }
    }

}
