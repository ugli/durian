package io.durian.model;

import io.durian.jaxen.DurianNavigator;
import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;

import java.util.List;

public interface Element extends Content, NamedNode {
    List<Content> content();

    List<? extends Attribute> attributes();

    default String path() {
        return parent().map(Element::path).map(p -> p + "/").orElse("") + name();
    }

    @SuppressWarnings("unchecked")
    default <T extends Node> List<T> select(String xpathExpr) {
		try {
            Navigator navigator = new DurianNavigator(this);
            BaseXPath xPath = new BaseXPath(xpathExpr, navigator);
            return xPath.selectNodes(this).stream().toList();
		} catch (JaxenException e) {
			throw new DurianException(e);
		}
    }

}
