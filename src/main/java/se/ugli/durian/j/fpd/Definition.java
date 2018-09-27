package se.ugli.durian.j.fpd;

import java.util.Optional;

import se.ugli.durian.j.dom.node.Node;

interface Definition {

	Optional<Node> createNode(String data);

	int numOfChars();

}
