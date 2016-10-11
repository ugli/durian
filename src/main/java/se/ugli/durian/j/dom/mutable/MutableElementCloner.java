package se.ugli.durian.j.dom.mutable;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.ElementCloner;
import se.ugli.durian.j.dom.node.NodeFactory;

class MutableElementCloner implements ElementCloner {

    private final MutableElement element;
    private final NodeFactory nodeFactory;
    private final String name;

    public MutableElementCloner(final MutableElement element) {
        this.element = element;
        this.nodeFactory = element.nodeFactory();
        this.name = element.getName();
    }

    @Override
    public <T extends Element> T element() {
        return ElementCloneCommand.cloneElement(name, element, nodeFactory);
    }

    @Override
    public <T extends Element> T element(final NodeFactory nodeFactory) {
        return ElementCloneCommand.cloneElement(name, element, nodeFactory);
    }

    @Override
    public <T extends Element> T element(final String elementName) {
        return ElementCloneCommand.cloneElement(name, element, nodeFactory);
    }

    @Override
    public <T extends Element> T element(final String elementName, final NodeFactory nodeFactory) {
        return ElementCloneCommand.cloneElement(name, element, nodeFactory);
    }

}
