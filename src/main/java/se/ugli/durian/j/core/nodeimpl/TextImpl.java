package se.ugli.durian.j.core.nodeimpl;

import java.util.Collection;
import java.util.Collections;

import se.ugli.durian.j.core.Content;
import se.ugli.durian.j.core.Text;

public class TextImpl implements Text {

    private final static Collection<Content> EMPTY_CONTENT = Collections.emptyList();

    private final String value;

    public TextImpl(final String value) {
	this.value = value;
    }

    @Override
    public Collection<Content> getContent() {
	return EMPTY_CONTENT;
    }

    @Override
    public String getValue() {
	return value;
    }

}
