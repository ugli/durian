package se.ugli.durian.jtidy;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.w3c.tidy.Tidy;

public class TidyFactory {

	private TidyFactory() {

	}

	private static class VoidWriter extends Writer {

		@Override
		public void write(final char[] cbuf, final int off, final int len) {
		}

		@Override
		public void flush() {
		}

		@Override
		public void close() {
		}

	}

	public static Tidy create() {
		return create(UTF_8);
	}

	public static Tidy create(final Charset charset) {
		final Tidy tidy = new Tidy();
		tidy.setShowWarnings(false);
		tidy.setShowErrors(0);
		tidy.setXmlTags(true);
		tidy.setInputEncoding(charset.name());
		tidy.setOutputEncoding(charset.name());
		tidy.setXHTML(true);
		tidy.setMakeClean(true);
		tidy.setErrout(new PrintWriter(new VoidWriter()));
		tidy.setOnlyErrors(true);
		return tidy;
	}

}
