package se.ugli.durian.j.dom.serialize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.parser.Parser;

public class XmlStringMatcher implements Matcher<String> {

    private final String expectedValue;

    public XmlStringMatcher(final String equalArg) {
        expectedValue = xmlWithSortedAttributes(equalArg);
    }

    private String xmlWithSortedAttributes(final String xmlStr) {
        final MutableElement element = Parser.apply().parse(xmlStr.getBytes());
        sortAttributes(element);
        return element.toXml();
    }

    private void sortAttributes(final MutableElement element) {
        final List<Attribute> list = new ArrayList<Attribute>((Collection<Attribute>) element.getAttributes());
        Collections.sort(list, new Comparator<Attribute>() {

            @Override
            public int compare(final Attribute o1, final Attribute o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        element.removeAll(Attribute.class);
        element.addAll(list);
        for (final Element childElement : element.getElements())
            sortAttributes((MutableElement) childElement);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendValue(expectedValue);
    }

    @Override
    public boolean matches(final Object item) {
        final String actualValue = xmlWithSortedAttributes((String) item);
        return expectedValue.equals(actualValue);
    }

    @Override
    public void describeMismatch(final Object item, final Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendValue(item);
    }

    @Override
    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
    }

}