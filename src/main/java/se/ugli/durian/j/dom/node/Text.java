package se.ugli.durian.j.dom.node;

public interface Text extends Content {

    String getValue();

    TextCloner clone();
}
