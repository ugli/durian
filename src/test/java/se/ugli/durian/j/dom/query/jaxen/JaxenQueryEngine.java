package se.ugli.durian.j.dom.query.jaxen;

import java.util.List;

import org.jaxen.JaxenException;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.query.QueryEngine;
import se.ugli.durian.j.dom.query.QueryException;

public class JaxenQueryEngine implements QueryEngine {

    @SuppressWarnings("unchecked")
    @Override
    public List<? extends Node> selectNodes(final Element element, final String path) {
        try {
            final DurianXpath durianXpath = new DurianXpath(path);
            return durianXpath.selectNodes(element);
        }
        catch (final JaxenException e) {
            throw new QueryException(e);
        }
    }

}
