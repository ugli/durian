package se.ugli.durian.j.dom.query.jaxen;

import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;

public class DurianXpath extends BaseXPath {

    private static final long serialVersionUID = 6191500970193005500L;

    protected DurianXpath(final String xpathExpr) throws JaxenException {
        super(xpathExpr, new DurianNavigator());
    }

}
