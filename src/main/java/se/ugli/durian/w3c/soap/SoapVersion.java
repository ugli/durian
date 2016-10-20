package se.ugli.durian.w3c.soap;

import static java.util.Arrays.stream;

import java.util.Optional;

public enum SoapVersion {

    V1_1("http://schemas.xmlsoap.org/soap/envelope/", "text/xml"),

    V1_2("http://www.w3.org/2003/05/soap-envelope", "application/soap+xml");

    public final String uri;
    public final String mimeType;

    SoapVersion(final String uri, final String mimeType) {
        this.uri = uri;
        this.mimeType = mimeType;
    }

    public static Optional<SoapVersion> fromUri(final String uri) {
        return stream(values()).filter(v -> v.uri.equals(uri)).findFirst();
    }
}
