package se.ugli.durian.j.json.jackson;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.java.io.Resource;

import static org.junit.jupiter.api.Assertions.*;

public class ParseSerializeTest {

    @Test
    public void xml2json() throws IOException {
        final MutableElement element = Durian.parseXml(Source.apply(Resource.apply("/person.xml")))
                .as(MutableElement.class);
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
        assertEquals("root", person.name());
        assertEquals("John Smith", person.attributeValue("name").get());

        final String actual = JsonSerializer.apply().serialize(person);

        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(getClass().getResourceAsStream("/person.json"));
        final String expected = objectMapper.writeValueAsString(jsonNode);

        assertEquals(expected, actual);
    }

    @Test
    public void json2xml() {

        final Element person = JsonParserBuilder.apply().rootElementName("person").createArrayElements(false).build()
                .parse(getClass().getResourceAsStream("/person.json"));
        assertNotNull(person);
        assertEquals("person", person.name());
        assertEquals("John Smith", person.attributeValue("name").get());
        assertFalse(person.attributeValue("xyz").isPresent());

        final String actual = person.toXml();

        final Element element = Durian.parseXml(Source.apply(Resource.apply("/person.xml")));

        final String expected = element.toXml();
        assertEquals(expected, actual);
    }

}
