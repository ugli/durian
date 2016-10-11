package se.ugli.durian.j.saxon;

import net.sf.saxon.pattern.NodeTest;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.type.Type;
import se.ugli.durian.j.dom.node.Text;

public class TextNodeInfo extends DurianNodeWrapper {

    private final Text text;

    public TextNodeInfo(final int index, final Text text, final DurianTreeInfo treeInfo) {
        super(index, Type.TEXT, text, treeInfo);
        this.text = text;
    }

    @Override
    public String getLocalPart() {
        return "";
    }

    @Override
    public String getURI() {
        return "";
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public CharSequence getStringValueCS() {
        return text.getValue();
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
