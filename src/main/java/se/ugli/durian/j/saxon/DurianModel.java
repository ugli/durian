package se.ugli.durian.j.saxon;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import net.sf.saxon.Configuration;
import net.sf.saxon.event.Builder;
import net.sf.saxon.event.PipelineConfiguration;
import net.sf.saxon.event.Receiver;
import net.sf.saxon.expr.JPConverter;
import net.sf.saxon.expr.PJConverter;
import net.sf.saxon.lib.ExternalObjectModel;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeModel;
import net.sf.saxon.trans.XPathException;
import se.ugli.durian.j.dom.node.Element;

public class DurianModel extends TreeModel implements ExternalObjectModel {

    @Override
    public String getDocumentClassName() {
        return Element.class.getName();
    }

    @Override
    public String getIdentifyingURI() {
        return "http://durian.io";
    }

    @Override
    public String getName() {
        return "Durian";
    }

    @SuppressWarnings("rawtypes")
    @Override
    public PJConverter getPJConverter(final Class targetClass) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public JPConverter getJPConverter(final Class sourceClass, final Configuration config) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PJConverter getNodeListCreator(final Object node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Receiver getDocumentBuilder(final Result result) throws XPathException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean sendSource(final Source source, final Receiver receiver) throws XPathException {
        return false;
    }

    @Override
    public NodeInfo unravel(final Source source, final Configuration config) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Builder makeBuilder(final PipelineConfiguration pipe) {
        return new DocumentBuilder();
    }

}
