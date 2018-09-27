package se.ugli.durian.w3c.soap;

import static java.util.Collections.singletonList;
import static se.ugli.durian.j.dom.node.PrefixMapping.prefixMapping;

import java.util.Optional;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.PrefixMapping;

public class Envelope extends MutableElement {

	public Envelope(final SoapVersion version) {
		this("Envelope", version.uri, new SoapNodeFactory(new MutableNodeFactory()),
				singletonList(prefixMapping("env", version.uri)));
		addElement("Header", version.uri, nodeFactory());
		addElement("Body", version.uri, nodeFactory());
	}

	public Envelope() {
		this(SoapVersion.V1_2);
	}

	Envelope(final String name, final String uri, final SoapNodeFactory nodeFactory,
			final Iterable<PrefixMapping> prefixMappings) {
		super(name, uri, nodeFactory, prefixMappings);
	}

	public Optional<Header> header() {
		return element("Header").map(e -> e.as(Header.class));
	}

	public Optional<Body> body() {
		return element("Body").map(e -> e.as(Body.class));
	}

	public Optional<Element> bodyContent() {
		return body().flatMap(e -> e.elements().findFirst());
	}

	public boolean hasFault() {
		return body().flatMap(Body::fault).isPresent();
	}

	public Optional<String> faultstring() {
		return body().flatMap(b -> b.fault().flatMap(Fault::faultstring));
	}

	public Optional<String> faultcode() {
		return body().flatMap(b -> b.fault().flatMap(Fault::faultcode));
	}

	public Optional<String> faultactor() {
		return body().flatMap(b -> b.fault().flatMap(Fault::faultactor));
	}

}
