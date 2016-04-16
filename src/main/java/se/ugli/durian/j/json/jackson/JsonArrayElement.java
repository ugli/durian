package se.ugli.durian.j.json.jackson;

import java.util.ArrayList;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Prefixmapping;

class JsonArrayElement extends MutableElement {

    final String arrayName;

    JsonArrayElement(final String arrayName, final String uri, final NodeFactory nodeFactory) {
        super("jsonArray", uri, nodeFactory, new ArrayList<Prefixmapping>(0));
        this.arrayName = arrayName;
    }

}
