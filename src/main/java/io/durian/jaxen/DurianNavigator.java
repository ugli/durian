package io.durian.jaxen;

import io.durian.dom.*;
import io.durian.dom.Content;
import io.durian.dom.Node;
import io.durian.dom.Text;
import org.jaxen.FunctionCallException;
import org.jaxen.Navigator;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.XPath;
import org.jaxen.pattern.Pattern;
import org.jaxen.util.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.singletonList;

public class DurianNavigator implements Navigator {

    private final Document document;

    public DurianNavigator(Element element) {
        document = new Document(getRoot(element));
    }

    private Element getRoot(Element element) {
        if (element.parent().isPresent())
            return getRoot(element.parent().get());
        return element;
    }

    @Override
    public String getAttributeName(Object attr) {
        Attribute attribute = (Attribute) attr;
        return attribute.name();
    }

    @Override
    public String getAttributeNamespaceUri(Object attr) {
        return null;
    }

    @Override
    public String getAttributeQName(Object attr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAttributeStringValue(Object attr) {
        Attribute attribute = (Attribute) attr;
        return attribute.value();
    }

    @Override
    public String getCommentStringValue(Object comment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getElementName(Object object) {
        Element element = (Element) object;
        return element.name();
    }

    @Override
    public String getElementNamespaceUri(Object object) {
        return null;
    }

    @Override
    public String getElementQName(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getElementStringValue(Object object) {
        Element element = (Element) object;
        StringBuilder stringBuilder = new StringBuilder();
        element.content()
                .stream()
                .filter(e -> e instanceof Text)
                .map(e -> (Text) e)
                .forEach(text -> stringBuilder.append(text.value()));
        return stringBuilder.toString();
    }

    @Override
    public String getNamespacePrefix(Object ns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNamespaceStringValue(Object ns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTextStringValue(Object text) {
        Text textObject = (Text) text;
        return textObject.value();
    }

    @Override
    public boolean isAttribute(Object object) {
        return object instanceof Attribute;
    }

    @Override
    public boolean isComment(Object object) {
        return false;
    }

    @Override
    public boolean isDocument(Object object) {
        return object instanceof Document;
    }

    @Override
    public boolean isElement(Object object) {
        return object instanceof Element;
    }

    @Override
    public boolean isNamespace(Object object) {
        return false;
    }

    @Override
    public boolean isProcessingInstruction(Object object) {
        return false;
    }

    @Override
    public boolean isText(Object object) {
        return object instanceof Text;
    }

    @Override
    public XPath parseXPath(String xpath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Content> getChildAxisIterator(Object contextNode) {
        if (contextNode instanceof Document) {
            List<Content> rootContent = new ArrayList<>();
            rootContent.add(document.root());
            return rootContent.iterator();
        } else if (contextNode instanceof Element element) {
            return element.content().iterator();
        } else if (contextNode instanceof Text)
            return new ArrayList<Content>().iterator();
        else
            throw new IllegalStateException(contextNode.getClass().getName());
    }

    @Override
    public Iterator<?> getDescendantAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new DescendantAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<? extends Element> getParentAxisIterator(Object contextNode) {
        if (contextNode instanceof Element element) {
            if (element.parent().isPresent())
                return singletonList(element.parent().get()).iterator();
        }
        return new ArrayList<Element>().iterator();
    }

    @Override
    public Iterator<?> getAncestorAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new AncestorAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getFollowingSiblingAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new FollowingSiblingAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getPrecedingSiblingAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new PrecedingSiblingAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getFollowingAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new FollowingAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getPrecedingAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new PrecedingAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<? extends Attribute> getAttributeAxisIterator(Object contextNode) {
        if (contextNode instanceof Element element)
            return element.attributes().iterator();
        return new ArrayList<Attribute>().iterator();
    }

    @Override
    public Iterator<?> getNamespaceAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new ArrayList<>().iterator();
    }

    @Override
    public Iterator<?> getSelfAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new SelfAxisIterator(contextNode);
    }

    @Override
    public Iterator<?> getDescendantOrSelfAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new DescendantOrSelfAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getAncestorOrSelfAxisIterator(Object contextNode) throws UnsupportedAxisException {
        return new AncestorOrSelfAxisIterator(contextNode, this);
    }

    @Override
    public Object getDocument(String uri) throws FunctionCallException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getDocumentNode(Object contextNode) {
        return document;
    }

    @Override
    public Object getParentNode(Object contextNode) {
        Node node = (Node) contextNode;
        return node.parent().orElse(null);
    }

    @Override
    public String getProcessingInstructionTarget(Object pi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getProcessingInstructionData(Object pi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String translateNamespacePrefixToUri(String prefix, Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getElementById(Object contextNode, String elementId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getNodeType(Object node) {
        if (isElement(node))
            return Pattern.ELEMENT_NODE;
        else if (isAttribute(node))
            return Pattern.ATTRIBUTE_NODE;
        else if (isText(node))
            return Pattern.TEXT_NODE;
        else if (isComment(node))
            return Pattern.COMMENT_NODE;
        else if (isDocument(node))
            return Pattern.DOCUMENT_NODE;
        else if (isProcessingInstruction(node))
            return Pattern.PROCESSING_INSTRUCTION_NODE;
        else if (isNamespace(node))
            return Pattern.NAMESPACE_NODE;
        else
            return Pattern.UNKNOWN_NODE;
    }

}
