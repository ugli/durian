package se.ugli.durian.j.dom.mutable;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static se.ugli.durian.j.dom.node.PrefixMapping.prefixMapping;

import java.util.List;

import org.junit.Test;

import se.ugli.durian.Durian;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.node.Text;

public class MutableElementTest {

    @Test
    public void shouldRemoveText() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);
        element.addText("hej");
        assertThat(element.texts().count(), is(1L));
        element.removeAll(Text.class);
        assertThat(element.texts().count(), is(0L));
    }

    @Test
    public void shouldShouldSetAttributeByNameIfNotExists() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);
        assertThat(element.attribute("a").isPresent(), is(false));
        element.setAttributeValue("a", "b");
        assertThat(element.attributeValue("a").get(), is("b"));
    }

    @Test
    public void shouldShouldSetAttributeByNameIfExists() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);
        element.addAttribute("a", "b");
        assertThat(element.attributeValue("a").get(), is("b"));
        element.setAttributeValue("a", "c");
        assertThat(element.attributeValue("a").get(), is("c"));
    }

    @Test
    public void shouldShouldRemveAttributeByNameIfValueIsNull() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);
        element.addAttribute("a", "b");
        assertThat(element.attributeValue("a").get(), is("b"));
        element.setAttributeValue("a", null);
        assertThat(element.attribute("a").isPresent(), is(false));
    }

    @Test
    public void shouldNotContainAnyPrefixMappings() {
        final MutableElement element = Durian.createElement("test").as(MutableElement.class);

        assertThat(element.prefixMappings().count(), is(0L));
    }

    @Test
    public void shouldNotContainTwoMappings() {
        final MutableElement element = Durian.createElement("test", prefixMapping("a", "b"), prefixMapping("c", "d"))
                .as(MutableElement.class);

        final List<PrefixMapping> prefixMappings = element.prefixMappings().collect(toList());
        assertThat(prefixMappings.size(), is(2));
        assertThat(prefixMappings.get(0), is(prefixMapping("a", "b")));
        assertThat(prefixMappings.get(1), is(prefixMapping("c", "d")));
    }

    @Test
    public void shouldNotContainOneMappings() {
        final MutableElement element = Durian
                .createElement("test", "b", prefixMapping("a", "b"), prefixMapping("c", "b")).as(MutableElement.class);
        final List<PrefixMapping> prefixMappings = element.prefixMappings().collect(toList());
        assertThat(prefixMappings.size(), is(1));
        assertThat(prefixMappings.get(0), is(prefixMapping("c", "b")));
    }

    @Test
    public void shouldSetText() {
        final MutableElement element = Durian.createElement("a").as(MutableElement.class);
        assertThat(element.hasTexts(), is(false));
        element.setText("x");
        assertThat(element.hasTexts(), is(true));
        assertThat(element.texts().map(Text::value).collect(joining()), is("x"));
    }

}
