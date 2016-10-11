package se.ugli.durian.j.dom.node;

public interface TextCloner {

    <T extends Text> T text();

    <T extends Text> T text(Element parent);

    <T extends Text> T text(NodeFactory nodeFactory);

    <T extends Text> T text(Element parent, NodeFactory nodeFactory);

}
