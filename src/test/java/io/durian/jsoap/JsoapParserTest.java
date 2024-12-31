package io.durian.jsoap;

import io.durian.dom.Element;
import io.durian.dom.Node;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsoapParserTest {

    @Test
    void parse() throws Exception {
        Element element = JsoapParser.parse(new URL("https://www.saob.se/artikel/?unik=R_3543-0295.04DG"));
        List<Node> select = element.select("//div[@class='rawcontent']/div[1]");
        assertEquals(1, select.size());
        Element div = select.get(0).asElement();
        String divHtml = """
                <div class="jamn">
                  <span class="StorRubrik indent">RÄGN </span>
                  <span class="StorKursiv">räŋ</span>
                  <span class="upphojd">4</span>
                  <span class="StorKursiv">n, </span>
                  <span class="LitenAntikva">n.; best. </span>
                  <span class="Formstil">-et; </span>
                  <span class="LitenAntikva">pl. =.</span>
                </div>
                """;
        assertEquals(divHtml.trim(), div.toString());
    }

}