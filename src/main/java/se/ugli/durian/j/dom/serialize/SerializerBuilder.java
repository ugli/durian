package se.ugli.durian.j.dom.serialize;

import java.util.LinkedHashMap;
import java.util.Map;

public final class SerializerBuilder {

    private final Map<String, String> prefixMapping = new LinkedHashMap<String, String>();

    private SerializerBuilder() {

    }

    public SerializerBuilder create() {
        return new SerializerBuilder();
    }

    public SerializerBuilder prefixMapping(final String uri, final String prefix) {
        prefixMapping.put(uri, prefix);
        return this;
    }

    public Serializer build() {
        return Serializer.apply(prefixMapping);
    }

}
