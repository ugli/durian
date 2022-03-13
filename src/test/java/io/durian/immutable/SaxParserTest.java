package io.durian.immutable;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaxParserTest {

    @Test
    void parse() {
        String xml = """
                <rot>
                  <barn>
                    <barnbarn a="11"/>
                  </barn>
                  <barn x="1" y="3">
                    <barnbarn bbb="qwqw"/>
                  </barn>
                </rot>
                """;
        Element element = SaxParser.parse(new StringReader(xml));
        assertEquals(xml.trim(), element.toXml());
    }

}