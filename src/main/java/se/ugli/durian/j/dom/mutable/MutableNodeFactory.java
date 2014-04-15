package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public class MutableNodeFactory implements NodeFactory {

    @SuppressWarnings({ "unchecked", "unused" })
    @Override
    public <T extends Attribute> T createAttribute(final String name, final String uri, final Element parent,
            final String value) {
        return (T) new MutableAttribute(name, uri, value);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @Override
    public <T extends Element> T createElement(final String name, final String uri, final Element parent) {
        return (T) new MutableElement(name, uri, this);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @Override
    public <T extends Text> T createText(final Element element, final String value) {
        return (T) new MutableText(value);
    }

}
