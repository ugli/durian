package se.ugli.durian.j.dom.serialize;

import java.util.Iterator;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Document;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.node.Text;

public class Serializer {

    public static String serialize(final Document document) {
        final StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        if (document.getRoot() != null) {
            serialize(document.getRoot(), stringBuffer, 0);
        }
        return stringBuffer.toString();
    }

    private static void serialize(final Element element, final StringBuilder stringBuffer, final int tab) {
        stringBuffer.append("\n");
        appendWithTab("<", stringBuffer, tab);
        stringBuffer.append(element.getQName());
        appendPrefixMapping(element, stringBuffer);
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
            stringBuffer.append(element.getQName());
            stringBuffer.append(">");
        }
    }

    private static void appendWithTab(final String str, final StringBuilder stringBuffer, final int tab) {
        for (int step = 0; step < tab - 1; step++) {
            stringBuffer.append("  ");
        }
        stringBuffer.append(str);
    }

    private static void appendPrefixMapping(final Element element, final StringBuilder stringBuffer) {
        if (element.getParent() == null && element.getDocument() != null) {
            for (final Iterator<PrefixMapping> i = element.getDocument().getPrefixMappings(); i.hasNext();) {
                final PrefixMapping prefixMapping = i.next();
                stringBuffer.append(" xmlns");
                if (prefixMapping.getPrefix() != null) {
                    stringBuffer.append(":");
                    stringBuffer.append(prefixMapping.getPrefix());
                }
                stringBuffer.append("=\"");
                stringBuffer.append(prefixMapping.getUri());
                stringBuffer.append("\"");
            }
        }
    }

    private static void appendAttributes(final Element element, final StringBuilder stringBuffer) {
        for (final Attribute attribute : element.getAttributes()) {
            stringBuffer.append(" ");
            stringBuffer.append(attribute.getName().getLocalName());
            stringBuffer.append("=\"");
            stringBuffer.append(attribute.getValue());
            stringBuffer.append("\"");
        }
    }

    private static void appendContent(final Element element, final StringBuilder stringBuffer, final int tab) {
        for (final Content content : element.getContent()) {
            if (content instanceof Element) {
                serialize((Element) content, stringBuffer, tab + 1);
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
