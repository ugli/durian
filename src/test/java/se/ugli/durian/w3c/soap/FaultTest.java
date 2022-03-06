package se.ugli.durian.w3c.soap;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FaultTest {

    @Test
    public void createFault() {
        final Envelope envelope = Fault.createEnvelopeWithFault("bad news");
        assertThat(envelope.select().text("/Envelope/Body/Fault/faultstring/text()").get(), is("bad news"));
    }

}
