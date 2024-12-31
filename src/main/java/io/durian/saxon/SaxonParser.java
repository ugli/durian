package io.durian.saxon;

import io.durian.dom.Element;
import lombok.SneakyThrows;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XdmNode;

import javax.xml.transform.stream.StreamSource;
import java.io.Reader;

public class SaxonParser {

    @SneakyThrows
    static Element parse(Reader reader) {
        Processor processor = new Processor(false);
        DocumentBuilder builder = processor.newDocumentBuilder();
        XdmNode xmlDocument = builder.build(new StreamSource(reader));
        return new SaxonElement(xmlDocument.children().iterator().next());
    }

}