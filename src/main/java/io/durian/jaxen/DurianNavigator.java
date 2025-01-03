package io.durian.jaxen;

import io.durian.Attribute;
import io.durian.Content;
import io.durian.Element;
import io.durian.Node;
import io.durian.Text;
import org.jaxen.Navigator;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.XPath;
import org.jaxen.util.AncestorAxisIterator;
import org.jaxen.util.AncestorOrSelfAxisIterator;
import org.jaxen.util.DescendantAxisIterator;
import org.jaxen.util.DescendantOrSelfAxisIterator;
import org.jaxen.util.FollowingAxisIterator;
import org.jaxen.util.FollowingSiblingAxisIterator;
import org.jaxen.util.PrecedingAxisIterator;
import org.jaxen.util.PrecedingSiblingAxisIterator;
import org.jaxen.util.SelfAxisIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.emptyIterator;
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
            return emptyIterator();
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
        return emptyIterator();
    }

    @Override
    public Iterator<?> getAncestorAxisIterator(Object contextNode) {
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
        return emptyIterator();
    }

    @Override
    public Iterator<?> getNamespaceAxisIterator(Object contextNode) {
        return emptyIterator();
    }

    @Override
    public Iterator<?> getSelfAxisIterator(Object contextNode) {
        return new SelfAxisIterator(contextNode);
    }

    @Override
    public Iterator<?> getDescendantOrSelfAxisIterator(Object contextNode) {
        return new DescendantOrSelfAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getAncestorOrSelfAxisIterator(Object contextNode) {
        return new AncestorOrSelfAxisIterator(contextNode, this);
    }

    @Override
    public Object getDocument(String uri) {
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
            return org.w3c.dom.Node.ELEMENT_NODE;
        if (isAttribute(node))
            return org.w3c.dom.Node.ATTRIBUTE_NODE;
        if (isText(node))
            return org.w3c.dom.Node.TEXT_NODE;
        if (isComment(node))
            return org.w3c.dom.Node.COMMENT_NODE;
        if (isDocument(node))
            return org.w3c.dom.Node.DOCUMENT_NODE;
        if (isProcessingInstruction(node))
            return org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE;
        if (isNamespace(node))
            return 13;
        return 14;
    }

}
