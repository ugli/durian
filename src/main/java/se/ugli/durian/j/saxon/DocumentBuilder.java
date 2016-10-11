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
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;

public class DocumentBuilder extends Builder {

    private DurianTreeInfo treeInfo;
    private final Stack<MutableElement> elementStack = new Stack<MutableElement>();
    private final NodeFactory nodeFactory = new MutableNodeFactory();
    private int index = 0;

    @Override
    public void startDocument(final int properties) {
        treeInfo = new DurianTreeInfo(getConfiguration());
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
        final MutableElement element = new MutableElement(elemName.getLocalPart(), uri, nodeFactory, new ArrayList<PrefixMapping>());
        treeInfo.registerElement(element, index++);
        elementStack.push(element);
        if (parent != null)
            parent.add(element);
        else
            treeInfo.setRoot(element);
    }

    @Override
    public void namespace(final NamespaceBinding namespaceBinding, final int properties) {
    }

    @Override
    public void attribute(final NodeName attName, final SimpleType typeCode, final CharSequence value, final Location location,
            final int properties) {
        final String uri = nonEmptyOrNull(attName.getURI());
        final MutableAttribute attribute = new MutableAttribute(attName.getLocalPart(), uri, value.toString(), nodeFactory);
        elementStack.peek().add(attribute);
        treeInfo.registerAttribute(attribute, index++);
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
            treeInfo.registerText(elementStack.peek().addText(value), index++);
    }

    @Override
    public void processingInstruction(final String name, final CharSequence data, final Location location, final int properties) {
    }

    @Override
    public void comment(final CharSequence content, final Location location, final int properties) {
    }

    @Override
    public NodeInfo getCurrentRoot() {
        return new DocumentNodeInfo(treeInfo);
    }

}