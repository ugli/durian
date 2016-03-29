package se.ugli.durian.j.json.jackson;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.NodeFactory;

class JsonArrayElement extends MutableElement {

    final String arrayName;

    JsonArrayElement(final String arrayName, final String uri, final NodeFactory nodeFactory) {
        super("jsonArray", uri, nodeFactory);
        this.arrayName = arrayName;
    }

}
