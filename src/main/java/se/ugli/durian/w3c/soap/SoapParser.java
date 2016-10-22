package se.ugli.durian.w3c.soap;

import javax.xml.parsers.SAXParser;

import se.ugli.durian.Source;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.parser.ParserBuilder;

public class SoapParser {

    public static Envelope parse(final Source source, final NodeFactory bodyNodeFactory, final SAXParser saxParser) {
        return ParserBuilder.apply().nodeFactory(new SoapNodeFactory(bodyNodeFactory)).saxParser(saxParser).build().parse(source)
                .as(Envelope.class);
    }

}
