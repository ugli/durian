package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.parser.Parser;
import se.ugli.durian.j.dom.serialize.Serializer;

public class JavaTest {

    public static void main(final String[] args) throws Exception {
        final Document document = Parser.apply().parseResource("/PurchaseOrder.sch");
        System.out.println(Serializer.serialize(document));
        System.out.println(document.getRoot().getContent().get(0).getPath());
    }
}
