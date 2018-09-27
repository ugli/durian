package se.ugli.durian.j.json.jackson;

import se.ugli.durian.j.dom.mutable.MutableNodeFactory;
import se.ugli.durian.j.dom.node.NodeFactory;

public final class JsonParserBuilder {

	private NodeFactory nodeFactory = new MutableNodeFactory();
	private String rootElementName = "root";
	private String uri = null;
	private boolean createArrayElements = true;
	private String rootArrayChildElementName = "rootArrayChild";

	private JsonParserBuilder() {

	}

	public static JsonParserBuilder apply() {
		return new JsonParserBuilder();
	}

	public JsonParserBuilder nodeFactory(final NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
		return this;
	}

	public JsonParserBuilder rootElementName(final String rootElementName) {
		this.rootElementName = rootElementName;
		return this;
	}

	public JsonParserBuilder uri(final String uri) {
		this.uri = uri;
		return this;
	}

	public JsonParserBuilder createArrayElements(final boolean createArrayElements) {
		this.createArrayElements = createArrayElements;
		return this;
	}

	public JsonParserBuilder rootArrayChildElementName(final String rootArrayChildElementName) {
		this.rootArrayChildElementName = rootArrayChildElementName;
		return this;
	}

	public JsonParser build() {
		return JsonParser.apply(this);
	}

	public NodeFactory getNodeFactory() {
		return nodeFactory;
	}

	public String getRootElementName() {
		return rootElementName;
	}

	public String getUri() {
		return uri;
	}

	public boolean doCreateArrayElements() {
		return createArrayElements;
	}

	public String getRootArrayChildElementName() {
		return rootArrayChildElementName;
	}

}
