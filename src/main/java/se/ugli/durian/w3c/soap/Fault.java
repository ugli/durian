package se.ugli.durian.w3c.soap;

import java.util.Optional;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.PrefixMapping;

public class Fault extends MutableElement {

	public Fault(final String name, final String uri, final NodeFactory nodeFactory,
			final Iterable<PrefixMapping> prefixMappings) {
		super(name, uri, nodeFactory, prefixMappings);
	}

	public Optional<String> faultcode() {
		return elementText("faultcode");
	}

	public Optional<String> faultstring() {
		return elementText("faultstring");
	}

	public void faultstring(final String faultstring) {
		if (!element("faultstring").isPresent())
			addElement("faultstring");
		element("faultstring").get().as(MutableElement.class).setText(faultstring);
	}

	public Optional<String> faultactor() {
		return elementText("faultactor");
	}

	public Optional<Element> detail() {
		return element("detail");
	}

	public Optional<Element> reason() {
		return element("Reason");
	}

	public static Envelope createEnvelopeWithFault(final String faultstring) {
		final Envelope envelope = new Envelope();
		envelope.body().get().addElement("Fault");
		envelope.body().get().fault().get().faultstring(faultstring);
		return envelope;
	}
}
