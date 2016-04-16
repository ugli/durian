package se.ugli.durian.j.dom.serialize;

public class DurianMatchers {

    public static XmlStringMatcher xmlEqualTo(final String xml) {
        return new XmlStringMatcher(xml);
    }

    public static XmlStringMatcher xmlEqualToResource(final String xml) {

        return new XmlStringMatcher(xml);
    }

}
