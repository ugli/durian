package io.durian.tidy;

import io.durian.model.Element;
import org.junit.jupiter.api.Test;

import java.net.URL;

class TidyParserTest {

    @Test
    void parse() throws Exception {
        Element element = TidyParser.parse(new URL("https://www.saob.se/artikel/?unik=R_3543-0295.04DG").openStream());
        System.out.println(element);
        //List<Node> select = element.select("//div[@class='rawcontent']");
        //System.out.println(select);
    }

}