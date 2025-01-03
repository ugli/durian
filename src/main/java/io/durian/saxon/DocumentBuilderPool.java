package io.durian.saxon;

import lombok.SneakyThrows;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DocumentBuilderPool {
    static final BlockingQueue<DocumentBuilder> DOCUMENT_BUILDER_POOL = new LinkedBlockingQueue<>();

    static {
        Processor processor = new Processor();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            DOCUMENT_BUILDER_POOL.add(processor.newDocumentBuilder());
        }
    }

    @SneakyThrows
    public static <T> T use(DocumentBuilderFunction<T> builderFunction) {
        DocumentBuilder builder = DOCUMENT_BUILDER_POOL.take();
        try {
            return builderFunction.apply(builder);
        } finally {
            DOCUMENT_BUILDER_POOL.offer(builder);
        }
    }

    public interface DocumentBuilderFunction<T> {
        T apply(DocumentBuilder builder) throws Exception;
    }

}