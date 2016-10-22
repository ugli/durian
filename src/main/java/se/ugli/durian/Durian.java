package se.ugli.durian;

import static java.util.Arrays.asList;

import org.w3c.dom.Document;

import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.parser.XmlParserBuilder;
import se.ugli.durian.j.fpd.FpdParser;
import se.ugli.durian.jtidy.HtmlParser;
import se.ugli.durian.w3c.dom.DocumentReader;
import se.ugli.durian.w3c.soap.Body;
import se.ugli.durian.w3c.soap.Envelope;
import se.ugli.durian.w3c.soap.Fault;
import se.ugli.durian.w3c.soap.SoapParserBuilder;

public class Durian {

    public static Element createElement(final String name, final PrefixMapping... prefixMappings) {
        return new MutableNodeFactory().createElement(name, null, null, asList(prefixMappings));
    }

    public static Element createElement(final String name, final String uri, final PrefixMapping... prefixMappings) {
        return new MutableNodeFactory().createElement(name, uri, null, asList(prefixMappings));
    }

    public static Envelope createSoapEnvelopeWithBody(final Node bodyNode) {
        return Body.createEnvelopeWithNode(bodyNode);
    }

    public static Envelope createSoapEnvelopeWithFault(final String faultstring) {
        return Fault.createEnvelopeWithFault(faultstring);
    }

    public static Element fromW3CDocument(final Document document) {
        return new DocumentReader(new MutableNodeFactory()).read(document);
    }

    public static Element parseFtp(final Source ftpDef, final String ftpData) {
        return new FpdParser(ftpDef, XmlParserBuilder.apply()).parse(ftpData);
    }

    public static Element parserHtml(final Source source) {
        return HtmlParser.parser(source);
    }

    public static Envelope parseSoap(final Source source) {
        return parseSoap(source, SoapParserBuilder.apply());
    }

    public static Envelope parseSoap(final Source source, final SoapParserBuilder builder) {
        return parseXml(source, builder).as(Envelope.class);
    }

    public static Element parseXml(final Source source) {
        return parseXml(source, XmlParserBuilder.apply());
    }

    public static Element parseXml(final Source source, final XmlParserBuilder builder) {
        return builder.build().parse(source);
    }

}