package io.durian.tidy;

import io.durian.model.Element;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TidyParser {

    static Element parse(InputStream inputStream) {
        Tidy tidy = new Tidy();
        tidy.setShowWarnings(false);
        tidy.setShowErrors(0);
//        tidy.setXmlTags(true);
        tidy.setInputEncoding(UTF_8.name());
        tidy.setOutputEncoding(UTF_8.name());
  //      tidy.setXHTML(true);
    //    tidy.setMakeClean(true);
        tidy.setErrout(new PrintWriter(new StringWriter()));
//        tidy.setOnlyErrors(true);
        Document document = tidy.parseDOM(inputStream, null);
        return new io.durian.dom.Element(document);
    }
}
