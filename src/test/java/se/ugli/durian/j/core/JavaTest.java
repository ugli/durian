package se.ugli.durian.j.core;

import java.io.InputStream;

import se.ugli.durian.j.core.nodeimpl.DefaultNodeFactory;
import se.ugli.durian.j.core.parser.Parser;

public class JavaTest {
    public static void main(final String[] args) throws Exception {
	final InputStream stream = JavaTest.class.getResourceAsStream("/PurchaseOrder.sch");

	final Parser saxParser = Parser.apply(new DefaultNodeFactory());
	final Document document = saxParser.parse(stream);
	System.out.println(Serializer.serialize(document));
    }
}
