package io.durian;

import io.durian.saxon.DocumentBuilderPool;
import io.durian.saxon.SaxonDocument;
import lombok.SneakyThrows;
import net.sf.saxon.s9api.XdmNode;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

public class Durian {

    public static Element wrap(org.w3c.dom.Document document) {
        return DocumentBuilderPool.use(builder -> {
            XdmNode saxonDocument = builder.wrap(document);
            return new SaxonDocument(saxonDocument).root();
        });
    }

    @SneakyThrows
    public static Element parseHtml(InputStream inputStream, Charset charset) {
        org.jsoup.nodes.Document jsoapDocument = Jsoup.parse(inputStream, charset.name(), "");
        org.w3c.dom.Document domDoc = W3CDom.convert(jsoapDocument);
        return wrap(domDoc);
    }

    @SneakyThrows
    public static Element parseXml(Reader reader) {
        return DocumentBuilderPool.use(builder -> {
            XdmNode xdmDocument = builder.build(new StreamSource(reader));
            return new SaxonDocument(xdmDocument).root();
        });
    }

}
