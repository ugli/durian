package io.durian.jsoap;

import io.durian.Element;
import io.durian.dom.ElementFactory;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JsoapParser {

    public static Element parse(URL url, int timeoutMillis) {
        return parse(() -> Jsoup.parse(url, timeoutMillis));
    }

    public static Element parse(URL url) {
        return parse(url, 5000);
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

    @FunctionalInterface
    interface ParseCmd {
        Document exec() throws IOException;
    }

    @SneakyThrows
    static Element parse(ParseCmd parseCmd) {
        Document jsoapDocument = parseCmd.exec();
        org.w3c.dom.Document domDoc = W3CDom.convert(jsoapDocument);
        return ElementFactory.create(domDoc);
    }

}
