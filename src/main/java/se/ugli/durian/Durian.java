package se.ugli.durian;

import static java.util.Arrays.asList;

import javax.xml.parsers.SAXParser;

import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.soap.Body;
import se.ugli.durian.soap.Envelope;
import se.ugli.durian.soap.Fault;
import se.ugli.durian.soap.SoapParser;

public class Durian {

    public static Element createElement(final String name, final PrefixMapping... prefixMappings) {
        return new MutableNodeFactory().createElement(name, null, null, asList(prefixMappings));
    }

    public static Element createElement(final String name, final String uri, final PrefixMapping... prefixMappings) {
        return new MutableNodeFactory().createElement(name, uri, null, asList(prefixMappings));
    }

    public static Envelope parseSoap(final String xml) {
        return SoapParser.parseString(xml, null, null);
    }

    public static Envelope parseSoap(final String xml, final SAXParser saxParser, final NodeFactory bodyNodeFactory) {
        return SoapParser.parseString(xml, saxParser, bodyNodeFactory);
    }

    public static Envelope parseSoap(final String xml, final SAXParser saxParser) {
        return SoapParser.parseString(xml, saxParser, null);
    }

    public static Envelope parseSoap(final String xml, final NodeFactory bodyNodeFactory) {
        return SoapParser.parseString(xml, null, bodyNodeFactory);
    }

    public static Envelope createSoapEnvelopeWithFault(final String faultstring) {
        return Fault.createEnvelopeWithFault(faultstring);
    }

    public static Envelope createSoapEnvelopeWithBody(final Node bodyNode) {
        return Body.createEnvelopeWithNode(bodyNode);
    }
}
