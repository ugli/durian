package se.ugli.durian.j.saxon;

import net.sf.saxon.pattern.NodeTest;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.util.Navigator;
import net.sf.saxon.type.Type;
import se.ugli.durian.j.dom.node.Element;

public class ElementNodeInfo extends DurianNodeWrapper {

    private final Element element;

    public ElementNodeInfo(final int index, final Element element, final DurianTreeInfo treeInfo) {
        super(index, Type.ELEMENT, element, treeInfo);
        this.element = element;
    }

    @Override
    public String getLocalPart() {
        return element.getName();
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
    public CharSequence getStringValueCS() {
        throw new UnsupportedOperationException();
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