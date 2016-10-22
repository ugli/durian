package se.ugli.durian.j.dom.serialize;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import se.ugli.durian.Durian;
import se.ugli.durian.Source;
import se.ugli.durian.j.dom.mutable.MutableElement;
import se.ugli.durian.j.dom.node.Attribute;

public class XmlStringMatcher implements Matcher<String> {

    private final String expectedValue;

    public XmlStringMatcher(final String equalArg) {
        expectedValue = xmlWithSortedAttributes(equalArg);
    }

    private String xmlWithSortedAttributes(final String xmlStr) {
        final MutableElement element = Durian.parseXml(Source.apply(xmlStr)).as(MutableElement.class);
        sortAttributes(element);
        return element.toXml();
    }

    private void sortAttributes(final MutableElement element) {
        final List<Attribute> list = element.attributes().collect(toList());
        Collections.sort(list, (o1, o2) -> o1.name().compareTo(o2.name()));
        element.removeAll(Attribute.class);
        element.addAll(list);
        element.elements().forEach(childElement -> {
            sortAttributes((MutableElement) childElement);
        });
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