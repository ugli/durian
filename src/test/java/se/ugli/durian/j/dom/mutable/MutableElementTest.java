package se.ugli.durian.j.dom.mutable;

import static com.google.common.collect.Iterables.size;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static se.ugli.durian.j.dom.node.PrefixMapping.prefixMapping;

import org.junit.Test;

import com.google.common.collect.Iterables;

import se.ugli.durian.Durian;
import se.ugli.durian.j.dom.node.Text;

public class MutableElementTest {

    @Test
    public void shouldRemoveText() {
        final MutableElement element = Durian.createElement("test");
        element.addText("hej");
        assertThat(size(element.getTexts()), is(1));
        element.removeAll(Text.class);
        assertThat(size(element.getTexts()), is(0));
    }

    @Test
    public void shouldShouldSetAttributeByNameIfNotExists() {
        final MutableElement element = Durian.createElement("test");
        assertThat(element.getAttributeByName("a"), nullValue());
        element.setAttributeValueByName("a", "b");
        assertThat(element.getAttributeValue("a"), is("b"));
    }

    @Test
    public void shouldShouldSetAttributeByNameIfExists() {
        final MutableElement element = Durian.createElement("test");
        element.addAttribute("a", "b");
        assertThat(element.getAttributeValue("a"), is("b"));
        element.setAttributeValueByName("a", "c");
        assertThat(element.getAttributeValue("a"), is("c"));
    }

    @Test
    public void shouldShouldRemveAttributeByNameIfValueIsNull() {
        final MutableElement element = Durian.createElement("test");
        element.addAttribute("a", "b");
        assertThat(element.getAttributeValue("a"), is("b"));
        element.setAttributeValueByName("a", null);
        assertThat(element.getAttributeByName("a"), nullValue());
    }

    @Test
    public void shouldNotContainAnyPrefixMappings() {
        final MutableElement element = Durian.createElement("test");

        assertThat(Iterables.isEmpty(element.prefixMappings()), is(true));
    }

    @Test
    public void shouldNotContainTwoMappings() {
        final MutableElement element = Durian.createElement("test", prefixMapping("a", "b"), prefixMapping("c", "d"));

        assertThat(Iterables.size(element.prefixMappings()), is(2));
        assertThat(Iterables.get(element.prefixMappings(), 0), is(prefixMapping("a", "b")));
        assertThat(Iterables.get(element.prefixMappings(), 1), is(prefixMapping("c", "d")));
    }

    @Test
    public void shouldNotContainOneMappings() {
        final MutableElement element = Durian.createElement("test", "b", prefixMapping("a", "b"), prefixMapping("c", "b"));

        assertThat(Iterables.size(element.prefixMappings()), is(1));
        assertThat(Iterables.get(element.prefixMappings(), 0), is(prefixMapping("c", "b")));

        System.out.println(element.toXml());
        System.out.println(Durian.createElement("dd", "abc").toXml());
    }

}
