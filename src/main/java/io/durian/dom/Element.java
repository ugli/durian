package io.durian.dom;

import io.durian.jaxen.DurianNavigator;
import io.durian.util.Serializer;
import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;

import java.util.List;
import java.util.function.Consumer;

public interface Element extends Content, NamedNode {
    List<Content> content();

    List<? extends Attribute> attributes();

    default String path() {
        return parent().map(Element::path).map(p -> p + "/").orElse("") + name();
    }

    @SuppressWarnings("unchecked")
    default List<Node> select(String xpathExpr) {
        try {
            Navigator navigator = new DurianNavigator(this);
            BaseXPath xPath = new BaseXPath(xpathExpr, navigator);
            return xPath.selectNodes(this).stream().toList();
        } catch (JaxenException e) {
            throw new DurianException(e);
        }
    }

    default void accept(Consumer<Node> visitor) {
        visitor.accept(this);
        attributes().forEach(visitor);
        content().forEach(c -> {
            if (c instanceof Element element)
                element.accept(visitor);
            else
                visitor.accept(c);
        });
    }

    default String toXml() {
        return Serializer.serialize(this);
    }

}
