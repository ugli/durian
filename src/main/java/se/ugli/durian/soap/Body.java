package se.ugli.durian.soap;

import java.util.Optional;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;

public class Body extends MutableElement {

    public Body(final String name, final String uri, final NodeFactory nodeFactory, final Iterable<PrefixMapping> prefixMappings) {
        super(name, uri, nodeFactory, prefixMappings);
    }

    public Optional<Fault> fault() {
        return element("Fault").map(e -> e.as(Fault.class));
    }

    public static Envelope createEnvelopeWithNode(final Node node) {
        final Envelope envelope = new Envelope();
        envelope.body().get().add(node);
        return envelope;
    }

}
