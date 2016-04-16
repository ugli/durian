package se.ugli.durian.j.fpd;

import se.ugli.durian.j.dom.node.Node;

interface Definition {

    Node createNode(String data);

    String name();

    int numOfChars();

}
