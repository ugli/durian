package se.ugli.durian.j.dom.mutable;

import static com.google.common.collect.Iterables.size;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Test;

import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Prefixmapping;
import se.ugli.durian.j.dom.node.Text;

public class MutableElementTest {

    @Test
    public void shouldRemoveText() {
        final String name = "test";
        final String uri = null;
        final NodeFactory nodeFactory = new MutableNodeFactory();
        final Iterable<Prefixmapping> prefixmappings = new ArrayList<Prefixmapping>();
        final MutableElement element = new MutableElement(name, uri, nodeFactory, prefixmappings);
        element.addText("hej");
        assertThat(size(element.getTexts()), is(1));
        element.removeAll(Text.class);
        assertThat(size(element.getTexts()), is(0));
    }

}
