package se.ugli.durian.j.dom.mutable;

import static com.google.common.collect.Iterables.size;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static se.ugli.durian.j.dom.node.PrefixMapping.prefixMapping;

import org.junit.Test;

import com.google.common.collect.Iterables;

import se.ugli.durian.Durian;
import se.ugli.durian.j.dom.node.Text;

public class MutableElementTest {

    @Test
    public void shouldRemoveText() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);
        element.addText("hej");
        assertThat(size(element.getTexts()), is(1));
        element.removeAll(Text.class);
        assertThat(size(element.getTexts()), is(0));
    }

    @Test
    public void shouldShouldSetAttributeByNameIfNotExists() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);
        assertThat(element.getAttributeByName("a").isPresent(), is(false));
        element.setAttributeValueByName("a", "b");
        assertThat(element.getAttributeValue("a").get(), is("b"));
    }

    @Test
    public void shouldShouldSetAttributeByNameIfExists() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);
        element.addAttribute("a", "b");
        assertThat(element.getAttributeValue("a").get(), is("b"));
        element.setAttributeValueByName("a", "c");
        assertThat(element.getAttributeValue("a").get(), is("c"));
    }

    @Test
    public void shouldShouldRemveAttributeByNameIfValueIsNull() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);
        element.addAttribute("a", "b");
        assertThat(element.getAttributeValue("a").get(), is("b"));
        element.setAttributeValueByName("a", null);
        assertThat(element.getAttributeByName("a").isPresent(), is(false));
    }

    @Test
    public void shouldNotContainAnyPrefixMappings() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);

        assertThat(Iterables.isEmpty(element.prefixMappings()), is(true));
    }

    @Test
    public void shouldNotContainTwoMappings() {
        final MutableElement element = Durian.createElement("test", prefixMapping("a", "b"), prefixMapping("c", "d"))
                .as(MutableElement.class);

        assertThat(Iterables.size(element.prefixMappings()), is(2));
        assertThat(Iterables.get(element.prefixMappings(), 0), is(prefixMapping("a", "b")));
        assertThat(Iterables.get(element.prefixMappings(), 1), is(prefixMapping("c", "d")));
    }

    @Test
    public void shouldNotContainOneMappings() {
        final MutableElement element = Durian.createElement("test", "b", prefixMapping("a", "b"), prefixMapping("c", "b"))
                .as(MutableElement.class);

        assertThat(Iterables.size(element.prefixMappings()), is(1));
        assertThat(Iterables.get(element.prefixMappings(), 0), is(prefixMapping("c", "b")));
    }

}
