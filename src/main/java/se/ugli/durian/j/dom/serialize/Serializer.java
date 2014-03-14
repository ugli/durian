package se.ugli.durian.j.dom.serialize;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Text;

public class Serializer {

    // TODO put in builder
    private static final String NAMESPACE_PREFIX_PREFIX = "ns";

    // TODO put in builder
    private static final String TAB = "  ";

    private final Map<String, String> prefixMapping;
    private int prefixNumber = 0;

    private Serializer(final Map<String, String> prefixMapping) {
        this.prefixMapping = prefixMapping;
    }

    public static Serializer apply() {
        return new Serializer(new LinkedHashMap<String, String>());
    }

    static Serializer apply(final Map<String, String> prefixMapping) {
        return new Serializer(prefixMapping);
    }

    public String serialize(final Element element) {
        final StringBuilder stringBuffer = new StringBuilder();
        // TODO handle encoding
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        serialize(element, stringBuffer, 1, true);
        return stringBuffer.toString();
    }

    private void serialize(final Element element, final StringBuilder stringBuffer, final int tab, final boolean root) {
        stringBuffer.append("\n");
        appendWithTab("<", stringBuffer, tab);
        final String qName = getQName(element, root);
        stringBuffer.append(qName);
        if (root) {
            appendPrefixMapping(element, stringBuffer);
        }
        appendAttributes(element, stringBuffer);
        if (element.getContent().isEmpty()) {
            stringBuffer.append("/>");
        }
        else {
            stringBuffer.append(">");
            appendContent(element, stringBuffer, tab);
            if (element.isSimpleTextNode()) {
                stringBuffer.append("</");
            }
            else {
                stringBuffer.append("\n");
                appendWithTab("</", stringBuffer, tab);
            }
            stringBuffer.append(qName);
            stringBuffer.append(">");
        }
    }

    private String getQName(final Element element, final boolean root) {
        if (root) {
            final String prefix = getPrefix(element);
            if (prefix != null) {
                return prefix + ":" + element.getName();
            }
        }
        return element.getName();
    }

    private String getPrefix(final Element element) {
        return getPrefix(element.getUri());
    }

    private synchronized String getPrefix(final String uri) {
        String prefix = prefixMapping.get(uri);
        if (prefix == null) {
            // TODO make block synchronized
            prefix = NAMESPACE_PREFIX_PREFIX + prefixNumber++;
            prefixMapping.put(uri, prefix);
        }
        return prefix;
    }

    private void appendWithTab(final String str, final StringBuilder stringBuffer, final int tab) {
        for (int step = 0; step < tab - 1; step++) {
            stringBuffer.append(TAB);
        }
        stringBuffer.append(str);
    }

    private void appendPrefixMapping(final Element element, final StringBuilder stringBuffer) {
        for (final String uri : element.getUriSet()) {
            final String prefix = getPrefix(uri);
            stringBuffer.append(" xmlns");
            if (prefix != null) {
                stringBuffer.append(":");
                stringBuffer.append(prefix);
            }
            stringBuffer.append("=\"");
            stringBuffer.append(uri);
            stringBuffer.append("\"");
        }
    }

    private void appendAttributes(final Element element, final StringBuilder stringBuffer) {
        for (final Attribute attribute : element.getAttributes()) {
            stringBuffer.append(" ");
            // TODO handle qname !?
            stringBuffer.append(attribute.getName());
            stringBuffer.append("=\"");
            stringBuffer.append(xmlEncode(attribute.getValue()));
            stringBuffer.append("\"");
        }
    }

    private void appendContent(final Element element, final StringBuilder stringBuffer, final int tab) {
        for (final Content content : element.getContent()) {
            if (content instanceof Element) {
                serialize((Element) content, stringBuffer, tab + 1, false);
            }
            else if (content instanceof Text) {
                final String textValue = ((Text) content).getValue();
                stringBuffer.append(xmlEncode(textValue));
            }
            else {
                throw new IllegalStateException(content.getClass().getName());
            }
        }
    }

    private String xmlEncode(final String textValue) {
        return StringEscapeUtils.escapeXml11(textValue);
    }
}
