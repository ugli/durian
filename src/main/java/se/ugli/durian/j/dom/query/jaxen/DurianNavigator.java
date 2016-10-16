package se.ugli.durian.j.dom.query.jaxen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jaxen.FunctionCallException;
import org.jaxen.Navigator;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.XPath;
import org.jaxen.pattern.Pattern;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.util.AncestorAxisIterator;
import org.jaxen.util.AncestorOrSelfAxisIterator;
import org.jaxen.util.DescendantAxisIterator;
import org.jaxen.util.DescendantOrSelfAxisIterator;
import org.jaxen.util.FollowingAxisIterator;
import org.jaxen.util.FollowingSiblingAxisIterator;
import org.jaxen.util.PrecedingAxisIterator;
import org.jaxen.util.PrecedingSiblingAxisIterator;
import org.jaxen.util.SelfAxisIterator;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Content;
import se.ugli.durian.j.dom.node.Element;
import se.ugli.durian.j.dom.node.Node;
import se.ugli.durian.j.dom.node.Text;

class DurianNavigator implements Navigator {

    private static final long serialVersionUID = 2313253033675992336L;

    private final Document document;

    public DurianNavigator(final Element element) {
        document = new Document(getRoot(element));
    }

    private Element getRoot(final Element element) {
        if (element.getParent().isPresent())
            return getRoot(element.getParent().get());
        return element;
    }

    @Override
    public String getAttributeName(final Object attr) {
        final Attribute attribute = (Attribute) attr;
        return attribute.getName();
    }

    @Override
    public String getAttributeNamespaceUri(final Object attr) {
        return null;
    }

    @Override
    public String getAttributeQName(final Object attr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAttributeStringValue(final Object attr) {
        final Attribute attribute = (Attribute) attr;
        return attribute.getValue();
    }

    @Override
    public String getCommentStringValue(final Object comment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getElementName(final Object object) {
        final Element element = (Element) object;
        return element.getName();
    }

    @Override
    public String getElementNamespaceUri(final Object object) {
        return null;
    }

    @Override
    public String getElementQName(final Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getElementStringValue(final Object object) {
        final Element element = (Element) object;
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Text text : element.getTexts())
            stringBuilder.append(text.getValue());
        return stringBuilder.toString();
    }

    @Override
    public String getNamespacePrefix(final Object ns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNamespaceStringValue(final Object ns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTextStringValue(final Object text) {
        final Text textObject = (Text) text;
        return textObject.getValue();
    }

    @Override
    public boolean isAttribute(final Object object) {
        return object instanceof Attribute;
    }

    @Override
    public boolean isComment(final Object object) {
        return false;
    }

    @Override
    public boolean isDocument(final Object object) {
        return object instanceof Document;
    }

    @Override
    public boolean isElement(final Object object) {
        return object instanceof Element;
    }

    @Override
    public boolean isNamespace(final Object object) {
        return false;
    }

    @Override
    public boolean isProcessingInstruction(final Object object) {
        return false;
    }

    @Override
    public boolean isText(final Object object) {
        return object instanceof Text;
    }

    @Override
    public XPath parseXPath(final String xpath) throws SAXPathException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Content> getChildAxisIterator(final Object contextNode) {
        if (contextNode instanceof Document) {
            final Document document = (Document) contextNode;
            final List<Content> rootContent = new ArrayList<Content>();
            rootContent.add(document.root);
            return rootContent.iterator();
        }
        else if (contextNode instanceof Element) {
            final Element element = (Element) contextNode;
            return element.getContent().iterator();
        }
        else if (contextNode instanceof Text)
            return new ArrayList<Content>().iterator();
        else
            throw new IllegalStateException(contextNode.getClass().getName());
    }

    @Override
    public Iterator<?> getDescendantAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new DescendantAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<Element> getParentAxisIterator(final Object contextNode) {
        if (contextNode instanceof Element) {
            final Element element = (Element) contextNode;
            if (element.getParent().isPresent())
                return Collections.singletonList(element.getParent().get()).iterator();
        }
        return new ArrayList<Element>().iterator();
    }

    @Override
    public Iterator<?> getAncestorAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new AncestorAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getFollowingSiblingAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new FollowingSiblingAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getPrecedingSiblingAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new PrecedingSiblingAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getFollowingAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new FollowingAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getPrecedingAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new PrecedingAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<Attribute> getAttributeAxisIterator(final Object contextNode) {
        if (contextNode instanceof Element) {
            final Element element = (Element) contextNode;
            return element.getAttributes().iterator();
        }
        return new ArrayList<Attribute>().iterator();
    }

    @Override
    public Iterator<?> getNamespaceAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new ArrayList<Object>().iterator();
    }

    @Override
    public Iterator<?> getSelfAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new SelfAxisIterator(contextNode);
    }

    @Override
    public Iterator<?> getDescendantOrSelfAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new DescendantOrSelfAxisIterator(contextNode, this);
    }

    @Override
    public Iterator<?> getAncestorOrSelfAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return new AncestorOrSelfAxisIterator(contextNode, this);
    }

    @Override
    public Object getDocument(final String uri) throws FunctionCallException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getDocumentNode(final Object contextNode) {
        return document;
    }

    @Override
    public Object getParentNode(final Object contextNode) throws UnsupportedAxisException {
        final Node node = (Node) contextNode;
        return node.getParent().orElse(null);
    }

    @Override
    public String getProcessingInstructionTarget(final Object pi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getProcessingInstructionData(final Object pi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String translateNamespacePrefixToUri(final String prefix, final Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getElementById(final Object contextNode, final String elementId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getNodeType(final Object node) {
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
