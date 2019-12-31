package cn.edu.sysu.workflow.engine.core.env.javascript;

import cn.edu.sysu.workflow.engine.core.Builtin;
import cn.edu.sysu.workflow.engine.core.Context;
import cn.edu.sysu.workflow.engine.core.SCXMLExpressionException;
import cn.edu.sysu.workflow.engine.core.XPathBuiltin;

import java.io.Serializable;

/**
 * Custom Javascript engine function providing the SCXML In() predicate and the Commons SCXML extensions
 * for Data() and Location() to support XPath datamodel access.
 */
public class JSFunctions implements Serializable {

    /**
     * The context currently in use for evaluation.
     */
    private Context ctx;

    /**
     * Creates a new instance, wraps the context.
     *
     * @param ctx the context in use
     */
    public JSFunctions(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Provides the SCXML standard In() predicate for SCXML documents.
     *
     * @param state The State ID to compare with
     * @return true if this state is currently active
     */
    public boolean In(final String state) {
        return Builtin.isMember(ctx, state);
    }

    /**
     * Provides the Commons SCXML Data() predicate extension for SCXML documents.
     *
     * @param expression the XPath expression
     * @return the data matching the expression
     * @throws SCXMLExpressionException A malformed expression exception
     */
    public Object Data(String expression) throws SCXMLExpressionException {
        return XPathBuiltin.eval(ctx, expression);
    }

    /**
     * Provides the Commons SCXML Location() predicate extension for SCXML documents.
     *
     * @param expression the XPath expression
     * @return the location matching the expression
     * @throws SCXMLExpressionException A malformed expression exception
     */
    public Object Location(String expression) throws SCXMLExpressionException {
        return XPathBuiltin.evalLocation(ctx, expression);
    }
}
