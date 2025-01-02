package io.durian;

import io.durian.jaxen.DurianNavigator;
import io.durian.util.Serializer;
import lombok.SneakyThrows;
import org.jaxen.BaseXPath;
import org.jaxen.Navigator;

import java.util.List;
import java.util.function.Consumer;

public interface Element extends Content, NamedNode {
    List<Content> content();

    List<? extends Attribute> attributes();

    default String path() {
        return parent().map(Node::path).map(p -> p + "/").orElse("") + name();
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    default List<Node> select(String xpathExpr) {
        Navigator navigator = new DurianNavigator(this);
        BaseXPath xPath = new BaseXPath(xpathExpr, navigator);
        return xPath.selectNodes(this).stream().toList();
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
