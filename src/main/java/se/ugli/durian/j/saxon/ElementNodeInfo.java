package se.ugli.durian.j.saxon;

import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.pattern.NodeTest;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.util.FastStringBuffer;
import net.sf.saxon.tree.util.Navigator;
import net.sf.saxon.tree.wrapper.AbstractNodeWrapper;
import net.sf.saxon.type.Type;
import se.ugli.durian.j.dom.node.Element;

public class ElementNodeInfo extends AbstractNodeWrapper {

    private final Element element;
    private final DurianTreeInfo treeInfo;

    public ElementNodeInfo(final Element element, final DurianTreeInfo treeInfo) {
        this.element = element;
        this.treeInfo = treeInfo;
    }

    @Override
    public TreeInfo getTreeInfo() {
        return treeInfo;
    }

    @Override
    public int getNodeKind() {
        return Type.ELEMENT;
    }

    @Override
    public String getURI() {
        return element.getUri() == null ? "" : element.getUri();
    }

    @Override
    public String getPrefix() {
        final String qName = element.qName();
        if (qName.contains(":"))
            return qName.substring(qName.indexOf(':'), qName.length());
        return "";
    }

    @Override
    public NodeInfo getParent() {
        if (element.getParent() != null)
            return new ElementNodeInfo(element.getParent(), treeInfo);
        return null;
    }

    @Override
    public int compareOrder(final NodeInfo other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLocalPart() {
        return element.getName();
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
        return element;
    }

    @Override
    protected AxisIterator iterateAttributes(final NodeTest nodeTest) {
        return new Navigator.AxisFilter(new AttributeIterator(element, treeInfo), nodeTest);
    }

    @Override
    protected AxisIterator iterateChildren(final NodeTest nodeTest) {
        return new Navigator.AxisFilter(new ContentIterator(element, treeInfo), nodeTest);
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