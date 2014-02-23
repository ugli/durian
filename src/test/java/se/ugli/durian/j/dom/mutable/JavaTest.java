package se.ugli.durian.j.dom.mutable;

import java.io.InputStream;

import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.parser.Parser;
import se.ugli.durian.j.dom.serialize.Serializer;

public class JavaTest {
    public static void main(final String[] args) throws Exception {
        final InputStream stream = JavaTest.class.getResourceAsStream("/PurchaseOrder.sch");

        final Parser saxParser = Parser.apply(new MutableNodeFactory());
        final Document document = saxParser.parse(stream);
        System.out.println(Serializer.serialize(document));
        System.out.println(document.getRoot().getContent().get(0).getPath());
    }
}
