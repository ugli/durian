package io.durian.immutable;

import io.durian.Attribute;
import io.durian.Content;
import io.durian.Element;
import io.durian.Namespace;
import io.durian.Node;
import io.durian.jaxen.DurianNavigator;
import lombok.Builder;
import lombok.SneakyThrows;
import org.jaxen.BaseXPath;
import org.jaxen.Navigator;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

@Builder
public record ImmutableElement(String id,
                               String name,
                               List<Content> _content,
                               List<Attribute> _attributes,
                               Optional<Element> parent,
                               Optional<Namespace> namespace) implements Element {
    @Override
    public List<Content> content() {
        return unmodifiableList(_content);
    }

    @Override
    public List<Attribute> attributes() {
        return unmodifiableList(_attributes);
    }

    @SneakyThrows
    @Override
    public List<Node> select(String xpathExpr) {
        Navigator navigator = new DurianNavigator(this);
        BaseXPath xPath = new BaseXPath(xpathExpr, navigator);
        return xPath.selectNodes(this);
    }

}
