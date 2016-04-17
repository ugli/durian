package se.ugli.durian.j.saxon;

import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Test;

import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;
import se.ugli.commons.Resource;
import se.ugli.durian.j.dom.parser.Parser;

public class DurianModelTest {

    @Test
    public void test() throws SaxonApiException {
        final Configuration config = new Configuration();
        final Processor processor = new Processor(config);
        final DocumentBuilder documentBuilder = processor.newDocumentBuilder();
        documentBuilder.setTreeModel(new DurianModel());
        final Resource resource = Resource.apply("/person.xml");
        final XdmNode xdmNode = documentBuilder.build(new StreamSource(resource.getInputStream()));
        final String saxonStr = xdmNode.toString();
        Assert.assertEquals(Parser.apply().parse(resource.getBytes()).toXml(), Parser.apply().parse(saxonStr.getBytes()).toXml());
    }

}
