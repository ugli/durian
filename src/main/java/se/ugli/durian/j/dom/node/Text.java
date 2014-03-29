package se.ugli.durian.j.dom.node;

public interface Text extends Content {

    <T extends Text> T cloneText();

    <T extends Text> T cloneText(NodeFactory nodeFactory);

    String getValue();
}
