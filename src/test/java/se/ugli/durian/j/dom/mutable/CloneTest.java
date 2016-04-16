package se.ugli.durian.j.dom.mutable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.parser.Parser;

public class CloneTest {

    @Test
    public void shouldClone() {
        final Element element = Parser.apply().parseResource("/se/ugli/durian/j/dom/mutable/ddata.xml");
        final Element elementClone = element.cloneElement();
        assertThat(element.toXml(), is(elementClone.toXml()));
    }

}
