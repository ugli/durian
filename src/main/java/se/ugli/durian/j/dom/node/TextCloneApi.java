package se.ugli.durian.j.dom.node;

public interface TextCloneApi {

    Text text();

    Text text(Element parent);

    Text text(NodeFactory nodeFactory);

    Text text(Element parent, NodeFactory nodeFactory);

}
