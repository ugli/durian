package io.durian.jsoap;

import io.durian.immutable.Attribute;
import io.durian.immutable.Text;
import io.durian.dom.Content;
import io.durian.dom.DurianException;
import io.durian.dom.Element;
import io.durian.dom.Namespace;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.durian.util.Serializer.serialize;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;

public class JsoapParser {

    public static Element parse(URL url, int timeoutMillis) {
         return parse(() -> Jsoup.parse(url, timeoutMillis));
    }

    public static Element parse(URL url) {
        return parse(url, 5000);
    }

    public static Element parse(File file, Charset charset) {
        return parse(() -> Jsoup.parse(file, charset.name()));
    }

    public static Element parse(File file) {
        return parse(file, UTF_8);
    }

    public static Element parse(InputStream inputStream, Charset charset) {
        return parse(() -> Jsoup.parse(inputStream, charset.name(), ""));
    }

    public static Element parse(InputStream inputStream) {
        return parse(inputStream, UTF_8);
    }

    public static Element parse(String html) {
        return parse(() -> Jsoup.parse(html));
    }

    public static Element parseBodyFragment(String html) {
        return parse(() -> Jsoup.parseBodyFragment(html));
    }

    @FunctionalInterface
    interface ParseCmd {
        Document exec() throws IOException;
    }

    static Element parse(ParseCmd parseCmd) {
        try {
            Document document = parseCmd.exec();
            return new JsoapElement(document.child(0), null);
        } catch (IOException e) {
            throw new DurianException(e);
        }
    }

    static class JsoapElement implements Element {
        private final String id = randomUUID().toString();
        private final String name;
        private final List<Content> content;
        private final List<Attribute> attributes;
        private final JsoapElement parent;

        JsoapElement(org.jsoup.nodes.Element element, JsoapElement parent) {
            this.name = element.nodeName();
            this.parent = parent;
            this.content = mapContent(element.childNodes());
            this.attributes = mapAttributes(element.attributes());
        }

        private List<Content> mapContent(List<Node> childNodes) {
            return childNodes.stream()
                    .map(this::mapContent)
                    .filter(Objects::nonNull)
                    .toList();
        }

        private Content mapContent(Node node) {
            if (node instanceof org.jsoup.nodes.Element element)
                return new JsoapElement(element, this);
            else if (node instanceof TextNode textNode) {
                String wholeText = textNode.getWholeText();
                if (!wholeText.trim().isBlank())
                    return new Text(wholeText, this);
            }
            else if (node instanceof DataNode dataNode) {
                return new Text(dataNode.getWholeData(), this);
            }
            return null;
        }

        private List<Attribute> mapAttributes(Attributes attributes) {
            return attributes.asList()
                    .stream()
                    .map(a -> new Attribute(a.getKey(), a.getValue(),this, null))
                    .toList();
        }

        @Override
        public List<Content> content() {
            return content;
        }

        @Override
        public List<Attribute> attributes() {
            return attributes;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public Optional<Namespace> namespace() {
            return empty();
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public Optional<Element> parent() {
            return ofNullable(parent);
        }

        @Override
        public String toString() {
            return serialize(this);
        }
    }

}
