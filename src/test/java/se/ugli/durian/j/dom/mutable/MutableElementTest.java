package se.ugli.durian.j.dom.mutable;

import static com.google.common.collect.Iterables.size;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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

    @Test
    public void shouldShouldSetAttributeByNameIfNotExists() {
        final String name = "test";
        final String uri = null;
        final NodeFactory nodeFactory = new MutableNodeFactory();
        final Iterable<Prefixmapping> prefixmappings = new ArrayList<Prefixmapping>();
        final MutableElement element = new MutableElement(name, uri, nodeFactory, prefixmappings);
        assertThat(element.getAttributeByName("a"), nullValue());
        element.setAttributeValueByName("a", "b");
        assertThat(element.getAttributeValue("a"), is("b"));
    }

    @Test
    public void shouldShouldSetAttributeByNameIfExists() {
        final String name = "test";
        final String uri = null;
        final NodeFactory nodeFactory = new MutableNodeFactory();
        final Iterable<Prefixmapping> prefixmappings = new ArrayList<Prefixmapping>();
        final MutableElement element = new MutableElement(name, uri, nodeFactory, prefixmappings);
        element.addAttribute("a", "b");
        assertThat(element.getAttributeValue("a"), is("b"));
        element.setAttributeValueByName("a", "c");
        assertThat(element.getAttributeValue("a"), is("c"));
    }

    @Test
    public void shouldShouldRemveAttributeByNameIfValueIsNull() {
        final String name = "test";
        final String uri = null;
        final NodeFactory nodeFactory = new MutableNodeFactory();
        final Iterable<Prefixmapping> prefixmappings = new ArrayList<Prefixmapping>();
        final MutableElement element = new MutableElement(name, uri, nodeFactory, prefixmappings);
        element.addAttribute("a", "b");
        assertThat(element.getAttributeValue("a"), is("b"));
        element.setAttributeValueByName("a", null);
        assertThat(element.getAttributeByName("a"), nullValue());
    }

}
