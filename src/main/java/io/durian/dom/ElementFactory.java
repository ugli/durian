package io.durian.dom;

import io.durian.Element;
import io.durian.saxon.SaxonDocument;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XdmNode;
import org.w3c.dom.Document;

public class ElementFactory {

    public static final Processor PROCESSOR = new Processor();

    public static Element create(Document document) {
        DocumentBuilder saxonBuilder = PROCESSOR.newDocumentBuilder();
        XdmNode saxonDocument = saxonBuilder.wrap(document);
        return new SaxonDocument(saxonDocument).root();
    }

}