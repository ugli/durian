package se.ugli.durian.w3c.soap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import se.ugli.commons.Resource;
import se.ugli.durian.Durian;
import se.ugli.durian.Source;

public class SoapParserTest {

    @Test
    public void shouldGetItem() {
        final Envelope envelope = Durian.parseSoap(Source.apply(Resource.apply("/soap-sample.xml")));
        assertThat(envelope.hasFault(), is(false));
        assertThat(envelope.select().text("//Item/text()").get(), is("Apples"));
    }

    @Test
    public void shouldParseFault() {
        final Envelope envelope = Durian.parseSoap(Source.apply(Resource.apply("/soap-fault.xml")));
        assertThat(envelope.hasFault(), is(true));
        assertThat(envelope.faultstring().get(), is("Failed to locate method"));
        assertThat(envelope.faultactor().get(), is("http://gizmos.com/order"));
    }

}
