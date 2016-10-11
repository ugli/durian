package se.ugli.durian.j.saxon;

import java.util.Iterator;

import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.AxisIteratorImpl;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;

public class AttributeIterator extends AxisIteratorImpl {

    private final Iterator<Attribute> iter;
    private final DurianTreeInfo treeInfo;
    private final Element element;

    public AttributeIterator(final Element element, final DurianTreeInfo treeInfo) {
        this.element = element;
        this.iter = element.getAttributes().iterator();
        this.treeInfo = treeInfo;
    }

    @Override
    public NodeInfo next() {
        if (iter.hasNext())
            return treeInfo.wrapper(iter.next());
        return null;
    }

    @Override
    public AxisIterator getAnother() {
        return new AttributeIterator(element, treeInfo);
    }

}
