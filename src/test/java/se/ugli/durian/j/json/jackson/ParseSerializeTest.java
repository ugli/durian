package se.ugli.durian.j.json.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.Element;

public class ParseSerializeTest {

    @Test
    public void xml2json() throws IOException {
        final MutableElement element = se.ugli.durian.j.dom.parser.Parser.apply().parse(getClass().getResourceAsStream("/person.xml"));
        element.addAttribute("xyz", null);
        final String actual = JsonSerializer.apply().serialize(element);

        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(getClass().getResourceAsStream("/person.json"));
        final String expected = objectMapper.writeValueAsString(jsonNode);

        assertEquals(expected, actual);

    }

    @Test
    public void json2json() throws IOException {

        final Element person = JsonParser.apply().parse(getClass().getResourceAsStream("/person.json"));
        assertNotNull(person);
        assertEquals("root", person.getName());
        assertEquals("John Smith", person.getAttributeValue("name"));

        final String actual = JsonSerializer.apply().serialize(person);

        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(getClass().getResourceAsStream("/person.json"));
        final String expected = objectMapper.writeValueAsString(jsonNode);

        assertEquals(expected, actual);
    }

    @Test
    public void json2xml() throws IOException {

        final Element person = JsonParserBuilder.apply().rootElementName("person").createArrayElements(false).build()
                .parse(getClass().getResourceAsStream("/person.json"));
        assertNotNull(person);
        assertEquals("person", person.getName());
        assertEquals("John Smith", person.getAttributeValue("name"));
        assertNull(person.getAttributeValue("xyz"));

        final String actual = person.toXml();

        final MutableElement element = se.ugli.durian.j.dom.parser.Parser.apply().parse(getClass().getResourceAsStream("/person.xml"));

        final String expected = element.toXml();
        assertEquals(expected, actual);
    }

}
