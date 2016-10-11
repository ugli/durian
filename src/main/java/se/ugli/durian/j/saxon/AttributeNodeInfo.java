package se.ugli.durian.j.saxon;

import net.sf.saxon.pattern.NodeTest;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.type.Type;
import se.ugli.durian.j.dom.node.Attribute;

public class AttributeNodeInfo extends DurianNodeWrapper {

    private final Attribute attribute;

    public AttributeNodeInfo(final int index, final Attribute attribute, final DurianTreeInfo treeInfo) {
        super(index, Type.ATTRIBUTE, attribute, treeInfo);
        this.attribute = attribute;
    }

    @Override
    public String getLocalPart() {
        return attribute.getName();
    }

    @Override
    public String getURI() {
        return attribute.getUri() == null ? "" : attribute.getUri();
    }

    @Override
    public String getPrefix() {
        final String qName = attribute.qName();
        if (qName.contains(":"))
            return qName.substring(qName.indexOf(':'), qName.length());
        return "";
    }

    @Override
    public CharSequence getStringValueCS() {
        return attribute.getValue();
    }

    @Override
    protected AxisIterator iterateAttributes(final NodeTest nodeTest) {
        return EmptyIterator.OfNodes.THE_INSTANCE;
    }

    @Override
    protected AxisIterator iterateChildren(final NodeTest nodeTest) {
        return EmptyIterator.OfNodes.THE_INSTANCE;
    }

    @Override
    protected AxisIterator iterateSiblings(final NodeTest nodeTest, final boolean forwards) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected AxisIterator iterateDescendants(final NodeTest nodeTest, final boolean includeSelf) {
        throw new UnsupportedOperationException();
    }

}