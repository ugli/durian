package se.ugli.durian.j.saxon;

import java.util.Iterator;

import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.AxisIteratorImpl;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Text;

public class ContentIterator extends AxisIteratorImpl {

    private final Element element;
    private final DurianTreeInfo treeInfo;
    private final Iterator<Content> iter;

    public ContentIterator(final Element element, final DurianTreeInfo treeInfo) {
        this.element = element;
        this.treeInfo = treeInfo;
        this.iter = element.getContent().iterator();
    }

    @Override
    public NodeInfo next() {
        if (iter.hasNext()) {
            final Content content = iter.next();
            if (content instanceof Element)
                return new ElementNodeInfo((Element) content, treeInfo);
            return new TextNodeInfo((Text) content, treeInfo);
        }
        return null;
    }

    @Override
    public AxisIterator getAnother() {
        return new ContentIterator(element, treeInfo);
    }

}
