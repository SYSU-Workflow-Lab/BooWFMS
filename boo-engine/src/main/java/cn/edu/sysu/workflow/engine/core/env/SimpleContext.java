package cn.edu.sysu.workflow.engine.core.env;

import cn.edu.sysu.workflow.engine.core.BOXMLSystemContext;
import cn.edu.sysu.workflow.engine.core.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple Context wrapping a map of variables.
 */
public class SimpleContext implements Context, Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Implementation independent log category.
     */
    private static final Log DEFAULT_LOG = LogFactory.getLog(Context.class);
    private Log log = DEFAULT_LOG;
    /**
     * The parent Context to this Context.
     */
    private Context parent;
    /**
     * The Map of variables and their values in this Context.
     */
    private Map<String, Object> vars;

    protected final BOXMLSystemContext systemContext;

    /**
     * Constructor.
     */
    public SimpleContext() {
        this(null, null);
    }

    /**
     * Constructor.
     *
     * @param parent A parent Context, can be null
     */
    public SimpleContext(final Context parent) {
        this(parent, null);
    }

    /**
     * Constructor.
     *
     * @param parent      A parent Context, can be null
     * @param initialVars A pre-populated initial variables map
     */
    public SimpleContext(final Context parent, final Map<String, Object> initialVars) {
        this.parent = parent;
        this.systemContext = parent instanceof BOXMLSystemContext ?
                (BOXMLSystemContext) parent : parent != null ? parent.getSystemContext() : null;
        if (initialVars == null) {
            setVars(new HashMap<String, Object>());
        } else {
            setVars(this.vars = initialVars);
        }
    }

    /**
     * Assigns a new value to an existing variable or creates a new one.
     * The method searches the chain of parent Contexts for variable
     * existence.
     *
     * @param name  The variable name
     * @param value The variable value
     * @see Context#set(String, Object)
     */
    public void set(final String name, final Object value) {
        if (getVars().containsKey(name)) { //first try to override local
            setLocal(name, value);
        } else if (parent != null && parent.has(name)) { //then check for global
            parent.set(name, value);
        } else { //otherwise create a new local variable
            setLocal(name, value);
        }
    }

    /**
     * Get the value of this variable; delegating to parent.
     *
     * @param name The variable name
     * @return Object The variable value
     * @see Context#get(String)
     */
    public Object get(final String name) {
        Object localValue = getVars().get(name);
        if (localValue != null) {
            return localValue;
        } else if (parent != null) {
            return parent.get(name);
        } else {
            return null;
        }
    }

    /**
     * Check if this variable exists, delegating to parent.
     *
     * @param name The variable name
     * @return boolean true if this variable exists
     * @see Context#has(String)
     */
    public boolean has(final String name) {
        return (hasLocal(name) || (parent != null && parent.has(name)));
    }

    /**
     * Check if this variable exists, only checking this Context
     *
     * @param name The variable name
     * @return boolean true if this variable exists
     * @see Context#hasLocal(String)
     */
    public boolean hasLocal(final String name) {
        return (getVars().containsKey(name));
    }

    /**
     * Clear this Context.
     *
     * @see Context#reset()
     */
    public void reset() {
        getVars().clear();
    }

    /**
     * Get the parent Context, may be null.
     *
     * @return Context The parent Context
     * @see Context#getParent()
     */
    public Context getParent() {
        return parent;
    }

    /**
     * Get the BOXMLSystemContext for this Context, should not be null unless this is the root Context
     *
     * @return The BOXMLSystemContext in a chained Context environment
     */
    public final BOXMLSystemContext getSystemContext() {
        return systemContext;
    }

    /**
     * Assigns a new value to an existing variable or creates a new one.
     * The method allows to shaddow a variable of the same name up the
     * Context chain.
     *
     * @param name  The variable name
     * @param value The variable value
     * @see Context#setLocal(String, Object)
     */
    public void setLocal(final String name, final Object value) {
        getVars().put(name, value);
        if (log.isDebugEnabled()) {
            log.debug(name + " = " + value);
        }
    }

    /**
     * Set the variables map.
     *
     * @param vars The new Map of variables.
     */
    protected void setVars(final Map<String, Object> vars) {
        this.vars = vars;
    }

    /**
     * Get the Map of all local variables in this Context.
     *
     * @return Returns the vars.
     */
    public Map<String, Object> getVars() {
        return vars;
    }

    /**
     * Set the log used by this <code>Context</code> instance.
     *
     * @param log The new log.
     */
    protected void setLog(final Log log) {
        this.log = log;
    }

    /**
     * Get the log used by this <code>Context</code> instance.
     *
     * @return Log The log being used.
     */
    protected Log getLog() {
        return log;
    }

}

