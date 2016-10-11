package se.ugli.durian.j.saxon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.Text;

class DurianTreeInfo implements TreeInfo {

    private Element root;
    private final Configuration configuration;

    DurianTreeInfo(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void setSystemId(final String systemId) {
        throw new UnsupportedOperationException();
    }

    public void setRoot(final Element root) {
        this.root = root;
    }

    @Override
    public String getSystemId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeInfo getRootNode() {
        return wrapper(root);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public long getDocumentNumber() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTyped() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeInfo selectID(final String id, final boolean getParent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<String> getUnparsedEntityNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getUnparsedEntity(final String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUserData(final String key, final Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getUserData(final String key) {
        throw new UnsupportedOperationException();
    }

    private final Map<String, NodeInfo> cache = new HashMap<String, NodeInfo>();

    void registerText(final Text text, final int index) {
        cache.put(text.id(), new TextNodeInfo(index, text, this));
    }

    void registerElement(final Element element, final int index) {
        cache.put(element.id(), new ElementNodeInfo(index, element, this));
    }

    void registerAttribute(final Attribute attribute, final int index) {
        cache.put(attribute.id(), new AttributeNodeInfo(index, attribute, this));
    }

    NodeInfo wrapper(final Node node) {
        if (node != null)
            return cache.get(node.id());
        return null;
    }

}