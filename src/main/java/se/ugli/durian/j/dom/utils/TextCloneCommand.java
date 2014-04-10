package se.ugli.durian.j.dom.utils;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.NodeFactory;
import se.ugli.durian.j.dom.node.Text;

public final class TextCloneCommand {

    private TextCloneCommand() {

    }

    public static <T extends Text> T execute(final Text textToClone, final Element parent, final NodeFactory nodeFactory) {
        final String value = textToClone.getValue();
        return nodeFactory.createText(parent, value);
    }

}
