package se.ugli.durian.j.fpd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.java.io.Resource;

public class FpdParserTest {

    @Test
    public void test1() {
        final Element element = Durian.parseFpd(Source.apply(Resource.apply("/se/ugli/durian/j/fpd/stdrec1.fpd")),
                Source.apply(Resource.apply("/se/ugli/durian/j/fpd/stdrec.txt")));
        assertEquals(element.toXml(), Resource.apply("/se/ugli/durian/j/fpd/stdrec1.xml").asString());
    }

    @Test
    public void test2() {
        final Element element = Durian.parseFpd(Source.apply(Resource.apply("/se/ugli/durian/j/fpd/stdrec2.fpd")),
                Source.apply(Resource.apply("/se/ugli/durian/j/fpd/stdrec.txt")));
        assertEquals(element.toXml(), Resource.apply("/se/ugli/durian/j/fpd/stdrec2.xml").asString());
    }

}
