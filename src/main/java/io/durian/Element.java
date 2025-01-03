package io.durian;

import java.util.List;
import java.util.function.Consumer;

public interface Element extends Content, NamedNode {
    List<Content> content();

    List<? extends Attribute> attributes();

    default String path() {
        return parent()
                .map(Node::path)
                .map(p -> p + "/")
                .orElse("")
                + name();
    }

    List<Node> select(String xpathExpr);

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

    String toXml();

}
