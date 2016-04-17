package se.ugli.durian.j.saxon;

import static se.ugli.durian.j.dom.utils.Strings.nonEmptyOrNull;

import java.util.ArrayList;
import java.util.Stack;

import net.sf.saxon.event.Builder;
import net.sf.saxon.expr.parser.Location;
import net.sf.saxon.om.NamespaceBinding;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.NodeName;
import net.sf.saxon.type.SchemaType;
import net.sf.saxon.type.SimpleType;
import se.ugli.durian.j.dom.mutable.MutableAttribute;
import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Prefixmapping;

public class DocumentBuilder extends Builder {

    private final Stack<MutableElement> elementStack = new Stack<MutableElement>();
    private final NodeFactory nodeFactory = new MutableNodeFactory();
    private Element root;

    @Override
    public void startDocument(final int properties) {
    }

    @Override
    public void endDocument() {
    }

    @Override
    public void setUnparsedEntity(final String name, final String systemID, final String publicID) {
    }

    @Override
    public void startElement(final NodeName elemName, final SchemaType typeCode, final Location location, final int properties) {
        final MutableElement parent = elementStack.isEmpty() ? null : elementStack.peek();
        final String uri = nonEmptyOrNull(elemName.getURI());
        final MutableElement element = new MutableElement(elemName.getLocalPart(), uri, nodeFactory, new ArrayList<Prefixmapping>());
        elementStack.push(element);
        if (parent != null)
            parent.add(element);
        else
            root = element;
    }

    @Override
    public void namespace(final NamespaceBinding namespaceBinding, final int properties) {
    }

    @Override
    public void attribute(final NodeName attName, final SimpleType typeCode, final CharSequence value, final Location location,
            final int properties) {
        final String uri = nonEmptyOrNull(attName.getURI());
        elementStack.peek().add(new MutableAttribute(attName.getLocalPart(), uri, value.toString()));
    }

    @Override
    public void startContent() {
    }

    @Override
    public void endElement() {
        elementStack.pop();
    }

    @Override
    public void characters(final CharSequence chars, final Location location, final int properties) {
        final String value = nonEmptyOrNull(chars.toString());
        if (value != null)
            elementStack.peek().addText(value);
    }

    @Override
    public void processingInstruction(final String name, final CharSequence data, final Location location, final int properties) {
    }

    @Override
    public void comment(final CharSequence content, final Location location, final int properties) {
    }

    @Override
    public NodeInfo getCurrentRoot() {
        return new DocumentNodeInfo(root, getConfiguration());
    }

}