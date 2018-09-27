package se.ugli.durian.j.dom.node;

public interface TextCloneApi {

	Text text();

	Text text(Element parent);

	Text text(Element parent, NodeFactory nodeFactory);

	Text text(NodeFactory nodeFactory);

}
