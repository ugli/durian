package se.ugli.durian.j.dom.serialize;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringEscapeUtils.escapeXml11;
import static se.ugli.durian.j.dom.serialize.SerializerBuilder.serializerBuilder;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.node.Text;

public class Serializer {

    private static final char LT = '<';
    private static final char GT = '>';
    private static final char SLASH = '/';
    private static final char SPACE = ' ';
    private static final char COLON = ':';
    private static final char QUOTE = '"';
    private static final char EQ = '=';
    private final String lineSeparator;
    private final String tab;
    private final String xmlVersion;
    private final String encoding;
    private final StringBuilder xml = new StringBuilder();
    private final Map<String, String> prefixByUri = new LinkedHashMap<String, String>();

    Serializer(final String xmlVersion, final String encoding, final String tab, final String lineSeparator) {
        this.xmlVersion = xmlVersion;
        this.encoding = encoding;
        this.tab = tab;
        this.lineSeparator = lineSeparator;
    }

    public static String serialize(final Element element) {
        return serializerBuilder().build().serializeDocument(element);
    }

    private String serializeDocument(final Element element) {
        appendDeclaration();
        appendElement(element, 0);
        return xml.toString();
    }

    private void appendDeclaration() {
        xml.append("<?xml");
        appendAttribute(xml, "version", xmlVersion);
        appendAttribute(xml, "encoding", encoding);
        xml.append("?>");
    }

    private void appendElement(final Element element, final int indentDepth) {
        final String qName = element.qName();
        final List<Content> content = element.content().collect(toList());
        xml.append(lineSeparator);
        appendCharWithTab(indentDepth, LT);
        xml.append(qName);
        appendPrefixmappings(element);
        appendAttributes(element.attributes().collect(toList()));
        if (content.isEmpty())
            xml.append(SPACE).append(SLASH).append(GT);
        else {
            xml.append(GT);
            appendContent(content, indentDepth);
            if (content.size() == 1 && content.get(0) instanceof Text)
                xml.append(LT).append(SLASH);
            else {
                xml.append(lineSeparator);
                appendCharWithTab(indentDepth, LT);
                xml.append(SLASH);
            }
            xml.append(qName).append(GT);
        }
    }

    private void appendStringWithTab(final int indentDepth, final String str) {
        for (int step = 0; step < indentDepth; step++)
            xml.append(tab);
        xml.append(str);
    }

    private void appendCharWithTab(final int indentDepth, final char ch) {
        for (int step = 0; step < indentDepth; step++)
            xml.append(tab);
        xml.append(ch);
    }

    private void appendPrefixmappings(final Element element) {
        element.prefixMappings().forEach(prefixMapping -> {
            prefixByUri.put(prefixMapping.uri, prefixMapping.prefix.orElse(null));
            appendPrefixMappping(prefixMapping);
        });
        final Optional<String> uri = element.uri();
        if (uri.isPresent() && !prefixByUri.containsKey(uri.get())) {
            final PrefixMapping prefixMapping = PrefixMapping.prefixMapping(null, uri.get());
            prefixByUri.put(prefixMapping.uri, prefixMapping.prefix.orElse(null));
            appendPrefixMappping(prefixMapping);
        }
    }

    private void appendPrefixMappping(final PrefixMapping prefixmapping) {
        xml.append(SPACE).append("xmlns");
        if (prefixmapping.prefix.isPresent())
            xml.append(COLON).append(prefixmapping.prefix.get());
        xml.append(EQ).append(QUOTE).append(prefixmapping.uri).append(QUOTE);
    }

    private void appendAttributes(final Iterable<Attribute> attributes) {
        for (final Attribute attribute : attributes)
            appendAttribute(xml, attribute.qName(), attribute.value());
    }

    private void appendAttribute(final StringBuilder xml, final String attributeName, final String attributeValue) {
        if (attributeValue != null)
            xml.append(SPACE).append(attributeName).append(EQ).append(QUOTE).append(escapeXml11(attributeValue))
                    .append(QUOTE);
    }

    private void appendContent(final Collection<Content> contentList, final int indentDepth) {
        for (final Content content : contentList)
            if (content instanceof Element)
                appendElement(content.as(Element.class), indentDepth + 1);
            else if (content instanceof Text) {
                final String textValue = content.as(Text.class).value();
                if (contentList.size() > 1) {
                    xml.append(lineSeparator);
                    appendStringWithTab(indentDepth + 1, escapeXml11(textValue));
                }
                else
                    xml.append(escapeXml11(textValue));
            }
            else
                throw new IllegalStateException(content.getClass().getName());
    }

}
