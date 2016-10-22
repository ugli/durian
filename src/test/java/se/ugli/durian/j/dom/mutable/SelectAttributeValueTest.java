package se.ugli.durian.j.dom.mutable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import se.ugli.commons.Resource;
import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;

public class SelectAttributeValueTest {

    @Test
    public void queryBinding() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        assertEquals("xpath2", element.select().attributeValue("/schema/@queryBinding").get());
    }

    @Test
    public void notFound() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        assertFalse(element.select().attributeValue("/schema/@queryBindings").isPresent());
    }

    @Test
    public void shouldSelectFirstAttribute() {
        final Element element = Durian.parseXml(Source.apply(Resource.apply("/PurchaseOrder.sch")));
        final Optional<String> attributeValue = element.select().attributeValue("//@test");
        assertThat(attributeValue.get(), is("parent::*/street"));
    }
}
