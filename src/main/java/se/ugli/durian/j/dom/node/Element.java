package se.ugli.durian.j.dom.node;


public interface Element extends Content {

    Name getName();

    Document getDocument();

    @Override
    Element getParent();

    Iterable<Attribute> getAttributes();

    Iterable<Element> getElements();

    Iterable<Text> getTexts();

    String getText();

    boolean isSimpleTextNode();

    boolean isRoot();

    void add(Node node);

    void remove(Node node);

}
