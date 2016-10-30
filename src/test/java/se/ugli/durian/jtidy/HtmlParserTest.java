package se.ugli.durian.jtidy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import se.ugli.commons.Resource;
import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;

public class HtmlParserTest {

    @Test
    public void shouldGetTitle() {
        final Element html = Durian.parserHtml(Source.apply(Resource.apply("/kth.index.html")));
        assertThat(html.select().text("/html/head/title/text()").get(), is("KTH | Välkommen till KTH"));
    }

}
