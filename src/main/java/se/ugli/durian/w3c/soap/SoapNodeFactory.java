package se.ugli.durian.w3c.soap;

import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;
import se.ugli.durian.j.dom.node.Text;

public class SoapNodeFactory implements NodeFactory {

    private final NodeFactory bodyNodeFactory;
    private final NodeFactory defaultNodeFactory = new MutableNodeFactory();

    public SoapNodeFactory(final NodeFactory bodyNodeFactory) {
        this.bodyNodeFactory = bodyNodeFactory;
    }

    @Override
    public Element createElement(final String name, final String uri, final Element parent,
            final Iterable<PrefixMapping> prefixMappings) {
        if (SoapVersion.fromUri(uri).isPresent())
            if ("Envelope".equals(name))
                return new Envelope(name, uri, this, prefixMappings);
            else if ("Header".equals(name))
                return new Header(name, uri, this, prefixMappings);
            else if ("Body".equals(name))
                return new Body(name, uri, this, prefixMappings);
            else if ("Fault".equals(name))
                return new Fault(name, uri, this, prefixMappings);
        return bodyNodeFactory.createElement(name, uri, parent, prefixMappings);
    }

    @Override
    public Attribute createAttribute(final String name, final String uri, final Element parent, final String value) {
        return defaultNodeFactory.createAttribute(name, uri, parent, value);
    }

    @Override
    public Text createText(final Element parent, final String value) {
        return defaultNodeFactory.createText(parent, value);
    }
}
