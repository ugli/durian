package se.ugli.durian;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import se.ugli.java.io.InputStreams;
import se.ugli.java.io.IoException;
import se.ugli.java.io.Readers;
import se.ugli.java.io.Resource;

@FunctionalInterface
public interface Source {

    byte[] data();

    static class SourceImpl implements Source {

        private final byte[] data;

        public SourceImpl(final byte[] data) {
            this.data = Arrays.copyOf(data, data.length);
        }

        @Override
        public byte[] data() {
            return Arrays.copyOf(data, data.length);
        }

    }

    static Source apply(final String s) {
        return new SourceImpl(s.getBytes());
    }

    static Source apply(final String s, final Charset charset) {
        return new SourceImpl(s.getBytes(charset));
    }

    static Source apply(final InputStream in) {
        return new SourceImpl(InputStreams.apply().copyToBytes(in));
    }

    static Source apply(final Reader in) {
        return new SourceImpl(Readers.apply().copyToBytes(in));
    }

    static Source apply(final Reader in, final Charset charset) {
        return new SourceImpl(InputStreams.apply().copyToBytes(in, charset));
    }

    static Source apply(final Resource resource) {
        return new SourceImpl(resource.asBytes());
    }

    static Source apply(final byte[] bytes) {
        return new SourceImpl(bytes);
    }

    static Source apply(final char[] chars) {
        return apply(String.valueOf(chars));
    }

    static Source apply(final char[] chars, final Charset charset) {
        return apply(String.valueOf(chars), charset);
    }

    static Source apply(final File file) {
        return apply(file.toPath());
    }

    static Source apply(final Path path) {
        try {
            return new SourceImpl(Files.readAllBytes(path));
        }
        catch (final IOException e) {
            throw new IoException(e);
        }
    }

    static Source apply(final URL url) {
        try (InputStream in = url.openStream()) {
            return apply(in);
        }
        catch (final IOException e) {
            throw new IoException(e);
        }
    }

}
