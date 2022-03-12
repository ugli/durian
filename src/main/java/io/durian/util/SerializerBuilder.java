package io.durian.util;

public final class SerializerBuilder {

	private String xmlVersion = "1.0";
	private String encoding = System.getProperty("file.encoding", "UTF-8");
	private String tab = "  ";
	private String lineSeparator = System.getProperty("line.separator");

	private SerializerBuilder() {

	}

	public static SerializerBuilder serializerBuilder() {
		return new SerializerBuilder();
	}

	public SerializerBuilder xmlVersion(final String xmlVersion) {
		this.xmlVersion = xmlVersion;
		return this;
	}

	public SerializerBuilder encoding(final String encoding) {
		this.encoding = encoding;
		return this;
	}

	public SerializerBuilder tab(final String tab) {
		this.tab = tab;
		return this;
	}

	public SerializerBuilder lineSeparator(final String lineSeparator) {
		this.lineSeparator = lineSeparator;
		return this;
	}

	public Serializer build() {
		return new Serializer(xmlVersion, encoding, tab, lineSeparator);
	}

}
