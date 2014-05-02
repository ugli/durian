package se.ugli.durian.j.dom.collections;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.mutable.MutableNodeFactory;

public class ListSynchronizerTest {

    @Test
    public void addAndRemoveElementInElements() {
        final MutableElement a = new MutableElement("a", null, new MutableNodeFactory());
        assertEquals(0, a.getElements().size());
        assertEquals(0, a.getContent().size());
        final MutableElement b = new MutableElement("b", null, new MutableNodeFactory());
        a.getElements().add(b);
        assertEquals(1, a.getElements().size());
        assertEquals(1, a.getContent().size());
        a.getElements().remove(b);
        assertEquals(0, a.getElements().size());
        assertEquals(0, a.getContent().size());
    }

    @Test
    public void addAndRemoveElementInContent() {
        final MutableElement a = new MutableElement("a", null, new MutableNodeFactory());
        assertEquals(0, a.getElements().size());
        assertEquals(0, a.getContent().size());
        final MutableElement b = new MutableElement("b", null, new MutableNodeFactory());
        a.getContent().add(b);
        assertEquals(1, a.getElements().size());
        assertEquals(1, a.getContent().size());
        a.getContent().remove(b);
        assertEquals(0, a.getElements().size());
        assertEquals(0, a.getContent().size());
    }

}
