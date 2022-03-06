package se.ugli.durian.jtidy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.java.io.Resource;

public class HtmlParserTest {

    @Test
    public void shouldGetTitle() {
        final Element html = Durian.parserHtml(Source.apply(Resource.apply("/kth.index.html")));
        assertThat(html.select().text("/html/head/title/text()").get(), is("KTH | Välkommen till KTH"));
    }

}
