package se.ugli.durian;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import se.ugli.commons.CopyCommand;
import se.ugli.commons.IoException;
import se.ugli.commons.Resource;

public interface Source {

    byte[] data();

    static class SourceImpl implements Source {

        private final byte[] data;

        public SourceImpl(final byte[] data) {
            this.data = data;
        }

        @Override
        public byte[] data() {
            return data;
        }

    }

    static Source apply(final String s) {
        return new SourceImpl(s.getBytes());
    }

    static Source apply(final String s, final Charset charset) {
        return new SourceImpl(s.getBytes(charset));
    }

    static Source apply(final InputStream in) {
        return new SourceImpl(CopyCommand.apply().copyToBytes(in));
    }

    static Source apply(final Reader in) {
        return new SourceImpl(CopyCommand.apply().copyToBytes(in));
    }

    static Source apply(final Reader in, final Charset charset) {
        return new SourceImpl(CopyCommand.apply().copyToBytes(in, charset));
    }

    static Source apply(final Resource resource) {
        return new SourceImpl(resource.getBytes());
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
