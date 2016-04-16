package se.ugli.durian.j.fpd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se.ugli.commons.Resource;
import se.ugli.durian.j.dom.node.Element;

public class FpdParserTest {

    @Test
    public void test1() {
        final byte[] definition = Resource.apply("/se/ugli/durian/j/fpd/stdrec1.fpd").getBytes();
        final String stdrec = Resource.apply("/se/ugli/durian/j/fpd/stdrec.txt").getString();
        final Element element = FpdParser.apply(definition).parse(stdrec);
        assertEquals(element.toXml(), Resource.apply("/se/ugli/durian/j/fpd/stdrec1.xml").getString());
    }

    @Test
    public void test2() {
        final byte[] definition = Resource.apply("/se/ugli/durian/j/fpd/stdrec2.fpd").getBytes();
        final String stdrec = Resource.apply("/se/ugli/durian/j/fpd/stdrec.txt").getString();
        final Element element = FpdParser.apply(definition).parse(stdrec);
        assertEquals(element.toXml(), Resource.apply("/se/ugli/durian/j/fpd/stdrec2.xml").getString());
    }

}
