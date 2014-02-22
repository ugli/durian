package se.ugli.durian.j.core;

import java.util.Collection;

public interface Element extends Content {

    Name getName();

    Document getDocument();

    Element getParent();

    Collection<? extends Attribute> getAttributes();

    boolean isSimpleTextNode();

    boolean isRoot();

    void append(Content content);

}
