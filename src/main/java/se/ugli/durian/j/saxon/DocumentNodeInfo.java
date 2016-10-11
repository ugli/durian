package se.ugli.durian.j.saxon;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.pattern.NodeTest;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.util.FastStringBuffer;
import net.sf.saxon.tree.wrapper.AbstractNodeWrapper;
import net.sf.saxon.type.Type;

public class DocumentNodeInfo extends AbstractNodeWrapper {

    private final DurianTreeInfo treeInfo;

    public DocumentNodeInfo(final DurianTreeInfo treeInfo) {
        this.treeInfo = treeInfo;
    }

    @Override
    public TreeInfo getTreeInfo() {
        return treeInfo;
    }

    @Override
    public Configuration getConfiguration() {
        return treeInfo.getConfiguration();
    }

    @Override
    public int getNodeKind() {
        return Type.DOCUMENT;
    }

    @Override
    public int compareOrder(final NodeInfo other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLocalPart() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getURI() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPrefix() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeInfo getParent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generateId(final FastStringBuffer buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharSequence getStringValueCS() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getUnderlyingNode() {
        throw new UnsupportedOperationException();
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