package se.ugli.durian.j.dom.serialize;

import java.util.HashMap;
import java.util.Map;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Text;

public class Serializer {

    private static final String TAB = "  ";
    // TODO
    @Deprecated
    private static Map<String, String> prefixMapping = new HashMap<String, String>();
    @Deprecated
    private static int prefixNumber = 0;

    public static String serialize(final Element element) {
        final StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        serialize(element, stringBuffer, 1, true);
        return stringBuffer.toString();
    }

    private static void serialize(final Element element, final StringBuilder stringBuffer, final int tab,
            final boolean root) {
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

    private static String getQName(final Element element, final boolean root) {
        if (root) {
            final String prefix = getPrefix(element);
            if (prefix != null) {
                return prefix + ":" + element.getName();
            }
        }
        return element.getName();
    }

    @Deprecated
    private static String getPrefix(final Element element) {
        return getPrefix(element.getUri());
    }

    @Deprecated
    private synchronized static String getPrefix(final String uri) {
        String prefix = prefixMapping.get(uri);
        if (prefix == null) {
            prefix = "ns" + prefixNumber++;
            prefixMapping.put(uri, prefix);
        }
        return prefix;
    }

    private static void appendWithTab(final String str, final StringBuilder stringBuffer, final int tab) {
        for (int step = 0; step < tab - 1; step++) {
            stringBuffer.append(TAB);
        }
        stringBuffer.append(str);
    }

    private static void appendPrefixMapping(final Element element, final StringBuilder stringBuffer) {
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

    private static void appendAttributes(final Element element, final StringBuilder stringBuffer) {
        for (final Attribute attribute : element.getAttributes()) {
            stringBuffer.append(" ");
            // TODO handle qname !?
            stringBuffer.append(attribute.getName());
            stringBuffer.append("=\"");
            stringBuffer.append(attribute.getValue());
            stringBuffer.append("\"");
        }
    }

    private static void appendContent(final Element element, final StringBuilder stringBuffer, final int tab) {
        for (final Content content : element.getContent()) {
            if (content instanceof Element) {
                serialize((Element) content, stringBuffer, tab + 1, false);
            }
            else if (content instanceof Text) {
                stringBuffer.append(((Text) content).getValue());
            }
            else {
                throw new IllegalStateException(content.getClass().getName());
            }
        }
    }
}
