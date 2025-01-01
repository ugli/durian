package io.durian.jsoap;

import io.durian.DurianException;
import io.durian.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import java.io.File;
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

}
