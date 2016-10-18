package se.ugli.durian.soap;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Test;

public class FaultTest {

    @Test
    public void createFault() {
        final Envelope envelope = Fault.createEnvelopeWithFault("bad news");
        Assert.assertThat(envelope.select().text("/Envelope/Body/Fault/faultstring/text()").get(), is("bad news"));
    }

}
