package se.ugli.durian.jtidy;

import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import se.ugli.durian.Source;
import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.w3c.dom.DocumentReader;

public class HtmlParser {

    public static Element parser(final Source source) {
        final Tidy tidy = TidyFactory.create();
        final Document document = tidy.parseDOM(new ByteArrayInputStream(source.data()), null);
        final DocumentReader documentReader = new DocumentReader(new MutableNodeFactory());
        return documentReader.read(document);
    }

}
