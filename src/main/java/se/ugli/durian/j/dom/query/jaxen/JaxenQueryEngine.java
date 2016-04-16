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
	public List<? extends Node> selectNodes(final Element element, final String query) {
		try {
			final DurianXpath durianXpath = new DurianXpath(element, query);
			return durianXpath.selectNodes(element);
		} catch (final JaxenException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public boolean evalateBoolean(Element element, String query) throws QueryException {
		try {
			final DurianXpath durianXpath = new DurianXpath(element, query);
			Object result = durianXpath.selectSingleNode(element);
			if (result == null)
				throw new QueryException("Query doesn't evalate a boolean. Result is null. Query: " + query);
			else if (!(result instanceof Boolean))
				throw new QueryException("Query doesn't evalate a boolean. Result has type: "
						+ result.getClass().getName() + " Query: " + query);
			return (Boolean) result;

		} catch (final JaxenException e) {
			throw new QueryException(e);
		}
	}

}