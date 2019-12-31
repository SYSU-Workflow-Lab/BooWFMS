package cn.edu.sysu.workflow.engine.core.env.jexl;

import cn.edu.sysu.workflow.engine.core.Builtin;
import cn.edu.sysu.workflow.engine.core.SCXMLExpressionException;
import cn.edu.sysu.workflow.engine.core.XPathBuiltin;

/**
 * Global JEXL namespace functor, providing the standard SCXML In() operator and the Commons SCXML extensions
 * for Data() and Location() to support XPath datamodel access.
 */
public final class JexlBuiltin {
    /**
     * The context currently in use for evaluation.
     */
    private final JexlContext context;

    /**
     * Creates a new instance, wraps the context.
     *
     * @param ctxt the context in use
     */
    public JexlBuiltin(final JexlContext ctxt) {
        context = ctxt;
    }

    /**
     * Provides the SCXML standard In() predicate for SCXML documents.
     *
     * @param state The State ID to compare with
     * @return true if this state is currently active
     */
    public boolean In(final String state) {
        return Builtin.isMember(context, state);
    }

    /**
     * Provides the Commons SCXML Data() predicate extension for SCXML documents.
     *
     * @param expression the XPath expression
     * @return the data matching the expression
     * @throws SCXMLExpressionException A malformed expression exception
     */
    public Object Data(final String expression) throws SCXMLExpressionException {
        return XPathBuiltin.eval(context, expression);
    }

    /**
     * Provides the Commons SCXML Location() predicate extension for SCXML documents.
     *
     * @param expression the XPath expression
     * @return the location matching the expression
     * @throws SCXMLExpressionException A malformed expression exception
     */
    public Object Location(final String expression) throws SCXMLExpressionException {
        return XPathBuiltin.evalLocation(context, expression);
    }
}
