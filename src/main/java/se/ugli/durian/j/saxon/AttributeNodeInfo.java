package se.ugli.durian.j.saxon;

import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.pattern.NodeTest;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.util.FastStringBuffer;
import net.sf.saxon.tree.wrapper.AbstractNodeWrapper;
import net.sf.saxon.type.Type;
import se.ugli.durian.j.dom.node.Attribute;

public class AttributeNodeInfo extends AbstractNodeWrapper {

    private final Attribute attribute;
    private final DurianTreeInfo treeInfo;

    public AttributeNodeInfo(final Attribute attribute, final DurianTreeInfo treeInfo) {
        this.attribute = attribute;
        this.treeInfo = treeInfo;
    }

    @Override
    public int getNodeKind() {
        return Type.ATTRIBUTE;
    }

    @Override
    public TreeInfo getTreeInfo() {
        return treeInfo;
    }

    @Override
    public int compareOrder(final NodeInfo other) {
        throw new UnsupportedOperationException();

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
    public NodeInfo getParent() {
        return new ElementNodeInfo(attribute.getParent(), treeInfo);
    }

    @Override
    public void generateId(final FastStringBuffer buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharSequence getStringValueCS() {
        return attribute.getValue();
    }

    @Override
    public Object getUnderlyingNode() {
        return attribute;
    }

    @Override
    protected AxisIterator iterateAttributes(final NodeTest nodeTest) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected AxisIterator iterateChildren(final NodeTest nodeTest) {
        throw new UnsupportedOperationException();
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