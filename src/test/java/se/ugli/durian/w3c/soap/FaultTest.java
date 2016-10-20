package se.ugli.durian.w3c.soap;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Test;

import se.ugli.durian.w3c.soap.Envelope;
import se.ugli.durian.w3c.soap.Fault;

public class FaultTest {

    @Test
    public void createFault() {
        final Envelope envelope = Fault.createEnvelopeWithFault("bad news");
        Assert.assertThat(envelope.select().text("/Envelope/Body/Fault/faultstring/text()").get(), is("bad news"));
    }

}
