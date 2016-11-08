package se.ugli.durian.j.dom.mutable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.java.io.Resource;

public class CloneTest {

    @Test
    public void shouldClone() {
        final Element element = Durian
                .parseXml(Source.apply(Resource.apply("/se/ugli/durian/j/dom/mutable/ddata.xml")));
        final Element elementClone = element.clone().element();
        assertThat(element.toXml(), is(elementClone.toXml()));
    }

}
