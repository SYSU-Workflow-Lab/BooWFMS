package cn.edu.sysu.workflow.resource.core.evaluator;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.MapContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Rinkako
 * Date  : 2018/2/9
 * Usage :
 */
public class RJexlMapContext extends MapContext implements REvaluableContext {

    private static final Logger log = LoggerFactory.getLogger(RJexlMapContext.class);

    /**
     * Internal context.
     */
    private JexlContext internalContext;

    /**
     * Create a new Jexl context.
     */
    public RJexlMapContext() {
        this.internalContext = new MapContext();
    }

    /**
     * Add a variable to context.
     *
     * @param name  variable name
     * @param value variable value
     */
    @Override
    public void Add(String name, Object value) {
        this.internalContext.set(name, value);
    }

    /**
     * Remove a variable and return it.
     *
     * @param name variable name
     * @return removed variable object, null if not exist.
     */
    @Override
    public Object Remove(String name) {
        Object retOne = this.internalContext.get(name);
        this.internalContext.set(name, null);
        return retOne;
    }

    /**
     * Update a variable in context.
     *
     * @param name  variable name
     * @param value variable value
     */
    @Override
    public void Update(String name, Object value) {
        this.internalContext.set(name, value);
    }

    /**
     * Retrieve a variable in context.
     *
     * @param name variable name
     * @return variable object, null if not exist.
     */
    @Override
    public Object Retrieve(String name) {
        return this.internalContext.get(name);
    }

    /**
     * Check if a variable name is defined in context.
     *
     * @param name variable name
     * @return true if exist
     */
    @Override
    public boolean Contains(String name) {
        return this.internalContext.has(name);
    }

    /**
     * Clear all variables in context.
     */
    @Override
    public void Clear() {
        log.warn("Clear is not valid for RJexlMapContext, ignored.");
    }

    /**
     * Count variables number in context.
     *
     * @return number of variables
     */
    @Override
    public int Count() {
        log.warn("Count is not valid for RJexlMapContext, always return 0.");
        return 0;
    }

    /**
     * Return internal context reference.
     *
     * @return internal context
     */
    @Override
    public Object GetInternalContext() {
        return this.internalContext;
    }
}
