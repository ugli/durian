package se.ugli.durian.soap;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;

import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.parser.ParserBuilder;

public class SoapParser {

    public static Envelope parseString(final String xml, final SAXParser saxParser, final NodeFactory bodyNodeFactory) {
        return ParserBuilder.apply().nodeFactory(new SoapNodeFactory(bodyNodeFactory)).saxParser(saxParser).build()
                .parse(new StringReader(xml)).as(Envelope.class);
    }

}
