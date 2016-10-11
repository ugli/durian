package se.ugli.durian.j.saxon;

import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.tree.util.FastStringBuffer;
import net.sf.saxon.tree.util.Navigator;
import net.sf.saxon.tree.wrapper.AbstractNodeWrapper;
import net.sf.saxon.tree.wrapper.SiblingCountingNode;
import se.ugli.durian.j.dom.node.Node;

abstract class DurianNodeWrapper extends AbstractNodeWrapper implements SiblingCountingNode {

    private final int index;
    private final int nodeKind;
    private final Node node;
    protected final DurianTreeInfo treeInfo;

    public DurianNodeWrapper(final int index, final int nodeKind, final Node node, final DurianTreeInfo treeInfo) {
        this.index = index;
        this.nodeKind = nodeKind;
        this.node = node;
        this.treeInfo = treeInfo;
    }

    @Override
    public int getNodeKind() {
        return nodeKind;
    }

    @Override
    public TreeInfo getTreeInfo() {
        return treeInfo;
    }

    @Override
    public int compareOrder(final NodeInfo other) {
        return Navigator.compareOrder(this, (SiblingCountingNode) other);
    }

    @Override
    public NodeInfo getParent() {
        return treeInfo.wrapper(node.getParent());
    }

    @Override
    public void generateId(final FastStringBuffer buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getUnderlyingNode() {
        return node;
    }

    @Override
    public int getSiblingPosition() {
        return index;
    }

}
