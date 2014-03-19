package se.ugli.durian.j.dom.query.simple;

import java.util.ArrayList;
import java.util.List;

import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.query.QueryEngine;

public class SimpleQueryEngine implements QueryEngine {

    @Override
    public List<? extends Node> selectNodes(final Element element, final String path) {
        return selectNodes(element, new Path(path));
    }

    private List<? extends Node> selectNodes(final Element element, final Path path) {
        if (path.isAbsolute()) {
            final Element root = getRoot(element);
            return selectAbsolute(root, path);
        }
        else if (path.isDescendantOrSelf()) {
            return selectDescendantOrSelf(element, path);
        }
        else if (path.isRelative()) {
            return selectRelative(element, path);
        }
        throw new IllegalStateException("Path: " + path);
    }

    private List<? extends Node> selectRelative(final Node node, final Path path) {
        final List<Node> nodes = new ArrayList<Node>();
        if (node.getPath().endsWith(path.value)) {
            nodes.add(node);
        }
        else if (node instanceof Element) {
            final Element element = (Element) node;
            for (final Node child : element.getContent()) {
                nodes.addAll(selectRelative(child, path));
            }
            for (final Node child : element.getAttributes()) {
                nodes.addAll(selectRelative(child, path));
            }
        }
        return nodes;
    }

    private List<? extends Node> selectDescendantOrSelf(final Node node, final Path path) {
        final List<Node> nodes = new ArrayList<Node>();
        final String relativePath = path.superAsRelative();
        if (node.getPath().endsWith(relativePath)) {
            nodes.add(node);
        }
        else if (node instanceof Element) {
            final Element element = (Element) node;
            for (final Node child : element.getContent()) {
                nodes.addAll(selectDescendantOrSelf(child, path));
            }
            for (final Node child : element.getAttributes()) {
                nodes.addAll(selectDescendantOrSelf(child, path));
            }
        }
        return nodes;
    }

    private List<? extends Node> selectAbsolute(final Node node, final Path path) {
        final List<Node> nodes = new ArrayList<Node>();
        if (node.getPath().equals(path.value)) {
            nodes.add(node);
        }
        else if (node instanceof Element) {
            final Element element = (Element) node;
            for (final Node child : element.getContent()) {
                nodes.addAll(selectAbsolute(child, path));
            }
            for (final Node child : element.getAttributes()) {
                nodes.addAll(selectAbsolute(child, path));
            }
        }
        return nodes;
    }

    private Element getRoot(final Element element) {
        if (element.getParent() == null) {
            return element;
        }
        return getRoot(element.getParent());
    }

}
