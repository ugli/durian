package se.ugli.durian.j.saxon;

import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.pattern.NodeTest;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.util.FastStringBuffer;
import net.sf.saxon.tree.wrapper.AbstractNodeWrapper;
import net.sf.saxon.type.Type;
import se.ugli.durian.j.dom.node.Text;

public class TextNodeInfo extends AbstractNodeWrapper {

    private final Text text;
    private final DurianTreeInfo treeInfo;

    public TextNodeInfo(final Text text, final DurianTreeInfo treeInfo) {
        this.text = text;
        this.treeInfo = treeInfo;
    }

    @Override
    public int getNodeKind() {
        return Type.TEXT;
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
        return text.getValue();
    }

    @Override
    public Object getUnderlyingNode() {
        return text;
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
