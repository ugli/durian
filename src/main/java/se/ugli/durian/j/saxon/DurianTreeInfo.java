package se.ugli.durian.j.saxon;

import java.util.Iterator;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import se.ugli.durian.j.dom.node.Element;

public class DurianTreeInfo implements TreeInfo {

    private final Element root;
    private final Configuration configuration;

    public DurianTreeInfo(final Element root, final Configuration configuration) {
        this.root = root;
        this.configuration = configuration;
    }

    @Override
    public void setSystemId(final String systemId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSystemId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeInfo getRootNode() {
        return new ElementNodeInfo(root, this);
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

}