package se.ugli.durian.j.dom.serialize;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se.ugli.commons.Resource;
import se.ugli.durian.Durian;
import se.ugli.durian.Source;

public class SerializerTest {

    @Test
    public void purchaseOrder() {
        final String resource = "/PurchaseOrder.sch";
        assertEquals(Resource.apply(resource).getString(), durianStr(resource));
    }

    @Test
    public void tables() {
        final String resource = "/se/ugli/durian/j/dom/serialize/tables.xml";
        assertEquals(Resource.apply(resource).getString(), durianStr(resource));
    }

    @Test
    public void tables2() {
        final String resource = "/se/ugli/durian/j/dom/serialize/tables2.xml";
        assertEquals(Resource.apply(resource).getString(), durianStr(resource));
    }

    @Test
    public void bar1() {
        final String resource = "/se/ugli/durian/j/dom/serialize/bar1.xml";
        assertEquals(Resource.apply(resource).getString(), durianStr(resource));
    }

    @Test
    public void bar2() {
        final String resource = "/se/ugli/durian/j/dom/serialize/bar2.xml";
        assertEquals(Resource.apply(resource).getString(), durianStr(resource));
    }

    @Test
    public void bar3() {
        final String resource = "/se/ugli/durian/j/dom/serialize/bar3.xml";
        assertEquals(Resource.apply(resource).getString(), durianStr(resource));
    }

    private String durianStr(final String resource) {
        return Durian.parseXml(Source.apply(Resource.apply(resource))).toXml();
    }

}
