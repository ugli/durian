package io.durian.saxon;

import io.durian.Element;
import lombok.SneakyThrows;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XdmNode;

import javax.xml.transform.stream.StreamSource;
import java.io.Reader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SaxonParser {
    static final BlockingQueue<DocumentBuilder> DOCUMENT_BUILDER_POOL = new LinkedBlockingQueue<>();

    static {
        Processor processor = new Processor();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            DOCUMENT_BUILDER_POOL.add(processor.newDocumentBuilder());
        }
    }

    @SneakyThrows
    public static Element parse(Reader reader) {
        DocumentBuilder builder = DOCUMENT_BUILDER_POOL.take();
        try {
            XdmNode xdmDocument = builder.build(new StreamSource(reader));
            return new SaxonDocument(xdmDocument).root();
        } finally {
            DOCUMENT_BUILDER_POOL.offer(builder);
        }
    }
}