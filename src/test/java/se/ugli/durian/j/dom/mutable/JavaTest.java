package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.parser.Parser;
import se.ugli.durian.j.dom.serialize.Serializer;

public class JavaTest {

    public static void main(final String[] args) throws Exception {
        final Element element = Parser.apply().parseResource("/PurchaseOrder.sch");
        System.out.println(Serializer.serialize(element));
        System.out.println(element.getContent().get(0).getPath());
    }
}
