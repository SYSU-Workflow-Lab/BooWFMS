package cn.edu.sysu.workflow.engine.core.env.groovy;

import cn.edu.sysu.workflow.engine.core.*;
import cn.edu.sysu.workflow.engine.core.env.EffectiveContextMap;
import cn.edu.sysu.workflow.engine.core.model.SCXML;
import groovy.lang.Script;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Evaluator implementation enabling use of Groovy expressions in SCXML documents.
 * <p/>
 * This implementation itself is thread-safe, so you can keep singleton for efficiency.
 * </P>
 */
public class GroovyEvaluator implements Evaluator, Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Unique context variable name used for temporary reference to assign data (thus must be a valid variable name)
     */
    private static final String ASSIGN_VARIABLE_NAME = "a" + UUID.randomUUID().toString().replace('-', 'x');

    public static final String SUPPORTED_DATA_MODEL = "groovy";

    public static class GroovyEvaluatorProvider implements EvaluatorProvider {


        public String getSupportedDatamodel() {
            return SUPPORTED_DATA_MODEL;
        }


        public Evaluator getEvaluator() {
            return new GroovyEvaluator();
        }


        public Evaluator getEvaluator(final SCXML document) {
            return new GroovyEvaluator();
        }
    }

    /**
     * Error message if evaluation context is not a GroovyContext.
     */
    private static final String ERR_CTX_TYPE = "Error evaluating Groovy "
            + "expression, Context must be a GroovyContext";

    protected static final GroovyExtendableScriptCache.ScriptPreProcessor scriptPreProcessor = new GroovyExtendableScriptCache.ScriptPreProcessor() {

        /**
         * Pattern for case-sensitive matching of the Groovy operator aliases, delimited by whitespace
         */
        public final Pattern GROOVY_OPERATOR_ALIASES_PATTERN = Pattern.compile("(?<=\\s)(and|or|not|eq|lt|le|ne|gt|ge)(?=\\s)");

        /**
         * Groovy operator aliases mapped to their underlying Groovy operator
         */
        public final Map<String, String> GROOVY_OPERATOR_ALIASES = Collections.unmodifiableMap(new HashMap<String, String>() {{
            put("and", "&& ");
            put("or", "||");
            put("not", " ! ");
            put("eq", "==");
            put("lt", "< ");
            put("le", "<=");
            put("ne", "!=");
            put("gt", "> ");
            put("ge", ">=");
        }});


        public String preProcess(final String script) {
            if (script == null || script.length() == 0) {
                return script;
            }
            StringBuffer sb = null;
            Matcher m = GROOVY_OPERATOR_ALIASES_PATTERN.matcher(script);
            while (m.find()) {
                if (sb == null) {
                    sb = new StringBuffer();
                }
                m.appendReplacement(sb, GROOVY_OPERATOR_ALIASES.get(m.group()));
            }
            if (sb != null) {
                m.appendTail(sb);
                return sb.toString();
            }
            return script;
        }
    };

    private final boolean useInitialScriptAsBaseScript;
    private final GroovyExtendableScriptCache scriptCache;

    public GroovyEvaluator() {
        this(false);
    }

    public GroovyEvaluator(boolean useInitialScriptAsBaseScript) {
        this.useInitialScriptAsBaseScript = useInitialScriptAsBaseScript;
        this.scriptCache = newScriptCache();
    }

    /**
     * Overridable factory method to create the GroovyExtendableScriptCache for this GroovyEvaluator.
     * <p>
     * The default implementation configures the scriptCache to use the {@link #scriptPreProcessor GroovyEvaluator scriptPreProcessor}
     * and the {@link GroovySCXMLScript} as script base class.
     * </p>
     *
     * @return GroovyExtendableScriptCache for this GroovyEvaluator
     */
    protected GroovyExtendableScriptCache newScriptCache() {
        GroovyExtendableScriptCache scriptCache = new GroovyExtendableScriptCache();
        scriptCache.setScriptPreProcessor(getScriptPreProcessor());
        scriptCache.setScriptBaseClass(GroovySCXMLScript.class.getName());
        return scriptCache;
    }

    @SuppressWarnings("unchecked")
    protected Script getScript(GroovyContext groovyContext, String scriptBaseClassName, String scriptSource) {
        Script script = scriptCache.getScript(scriptBaseClassName, scriptSource);
        script.setBinding(groovyContext.getBinding());
        return script;
    }

    @SuppressWarnings("unused")
    public void clearCache() {
        scriptCache.clearCache();
    }

    public GroovyExtendableScriptCache.ScriptPreProcessor getScriptPreProcessor() {
        return scriptPreProcessor;
    }

    /* SCXMLEvaluator implementation methods */


    public String getSupportedDatamodel() {
        return SUPPORTED_DATA_MODEL;
    }

    /**
     * Evaluate an expression.
     *
     * @param ctx  variable context
     * @param expr expression
     * @return a result of the evaluation
     * @throws SCXMLExpressionException For a malformed expression
     * @see Evaluator#eval(Context, String)
     */

    public Object eval(final Context ctx, final String expr) throws SCXMLExpressionException {
        if (expr == null) {
            return null;
        }

        if (!(ctx instanceof GroovyContext)) {
            throw new SCXMLExpressionException(ERR_CTX_TYPE);
        }

        final GroovyContext groovyCtx = (GroovyContext) ctx;
        if (groovyCtx.getGroovyEvaluator() == null) {
            groovyCtx.setGroovyEvaluator(this);
        }
        try {
            return getScript(getEffectiveContext(groovyCtx), groovyCtx.getScriptBaseClass(), expr).run();
        } catch (Exception e) {
            String exMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName();
            throw new SCXMLExpressionException("eval('" + expr + "'): " + exMessage, e);
        }
    }

    /**
     * @see Evaluator#evalCond(Context, String)
     */

    public Boolean evalCond(final Context ctx, final String expr) throws SCXMLExpressionException {
        if (expr == null) {
            return null;
        }

        if (!(ctx instanceof GroovyContext)) {
            throw new SCXMLExpressionException(ERR_CTX_TYPE);
        }

        final GroovyContext groovyCtx = (GroovyContext) ctx;
        if (groovyCtx.getGroovyEvaluator() == null) {
            groovyCtx.setGroovyEvaluator(this);
        }
        try {
            final Object result = getScript(getEffectiveContext(groovyCtx), groovyCtx.getScriptBaseClass(), expr).run();
            return result == null ? Boolean.FALSE : (Boolean) result;
        } catch (Exception e) {
            String exMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName();
            throw new SCXMLExpressionException("evalCond('" + expr + "'): " + exMessage, e);
        }
    }

    /**
     * @see Evaluator#evalLocation(Context, String)
     */

    public Object evalLocation(final Context ctx, final String expr) throws SCXMLExpressionException {
        if (expr == null) {
            return null;
        } else if (ctx.has(expr)) {
            return expr;
        }

        if (!(ctx instanceof GroovyContext)) {
            throw new SCXMLExpressionException(ERR_CTX_TYPE);
        }

        GroovyContext groovyCtx = (GroovyContext) ctx;
        if (groovyCtx.getGroovyEvaluator() == null) {
            groovyCtx.setGroovyEvaluator(this);
        }
        try {
            final GroovyContext effective = getEffectiveContext(groovyCtx);
            return getScript(effective, groovyCtx.getScriptBaseClass(), expr).run();
        } catch (Exception e) {
            String exMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName();
            throw new SCXMLExpressionException("evalLocation('" + expr + "'): " + exMessage, e);
        }
    }

    /**
     * @see Evaluator#evalAssign(Context, String, Object, AssignType, String)
     */
    public void evalAssign(final Context ctx, final String location, final Object data, final AssignType type,
                           final String attr) throws SCXMLExpressionException {

        final Object loc = evalLocation(ctx, location);
        if (loc != null) {

            if (XPathBuiltin.isXPathLocation(ctx, loc)) {
                XPathBuiltin.assign(ctx, loc, data, type, attr);
            } else {
                final StringBuilder sb = new StringBuilder(location).append("=").append(ASSIGN_VARIABLE_NAME);
                try {
                    ctx.getVars().put(ASSIGN_VARIABLE_NAME, data);
                    eval(ctx, sb.toString());
                } finally {
                    ctx.getVars().remove(ASSIGN_VARIABLE_NAME);
                }
            }
        } else {
            throw new SCXMLExpressionException("evalAssign - cannot resolve location: '" + location + "'");
        }
    }

    /**
     * @see Evaluator#evalScript(Context, String)
     */

    public Object evalScript(final Context ctx, final String scriptSource) throws SCXMLExpressionException {
        if (scriptSource == null) {
            return null;
        }

        if (!(ctx instanceof GroovyContext)) {
            throw new SCXMLExpressionException(ERR_CTX_TYPE);
        }

        final GroovyContext groovyCtx = (GroovyContext) ctx;
        if (groovyCtx.getGroovyEvaluator() == null) {
            groovyCtx.setGroovyEvaluator(this);
        }
        try {
            final GroovyContext effective = getEffectiveContext(groovyCtx);
            final boolean inGlobalContext = groovyCtx.getParent() instanceof BOXMLSystemContext;
            final Script script = getScript(effective, groovyCtx.getScriptBaseClass(), scriptSource);
            final Object result = script.run();
            if (inGlobalContext && useInitialScriptAsBaseScript) {
                groovyCtx.setScriptBaseClass(script.getClass().getName());
            }
            return result;
        } catch (Exception e) {
            final String exMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName();
            throw new SCXMLExpressionException("evalScript('" + scriptSource + "'): " + exMessage, e);
        }
    }

    protected ClassLoader getGroovyClassLoader() {
        return scriptCache.getGroovyClassLoader();
    }

    /**
     * Create a new child context.
     *
     * @param parent parent context
     * @return new child context
     * @see Evaluator#newContext(Context)
     */

    public Context newContext(final Context parent) {
        return new GroovyContext(parent, this);
    }

    /**
     * Create a new context which is the summation of contexts from the
     * current state to document root, child has priority over parent
     * in scoping rules.
     *
     * @param nodeCtx The GroovyContext for this state.
     * @return The effective GroovyContext for the path leading up to
     * document root.
     */
    protected GroovyContext getEffectiveContext(final GroovyContext nodeCtx) {
        return new GroovyContext(nodeCtx, new EffectiveContextMap(nodeCtx), this);
    }
}