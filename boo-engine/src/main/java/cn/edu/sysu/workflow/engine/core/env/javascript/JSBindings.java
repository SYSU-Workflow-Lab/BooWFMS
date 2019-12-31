package cn.edu.sysu.workflow.engine.core.env.javascript;

import cn.edu.sysu.workflow.engine.core.Context;

import javax.script.Bindings;
import javax.script.SimpleBindings;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper class for the JDK Javascript engine Bindings class that extends the
 * wrapped Bindings to search the SCXML context for variables and predefined
 * functions that do not exist in the wrapped Bindings.
 */
public class JSBindings implements Bindings {

    private static final String NASHORN_GLOBAL = "nashorn.global";

    // INSTANCE VARIABLES

    private Bindings bindings;
    private Context context;

    // CONSTRUCTORS

    /**
     * Initialises the internal Bindings delegate and SCXML context.
     *
     * @param context  SCXML Context to use for script variables.
     * @param bindings Javascript engine bindings for Javascript variables.
     * @throws IllegalArgumentException Thrown if either <code>context</code>
     *                                  or <code>bindings</code> is <code>null</code>.
     */
    public JSBindings(Context context, Bindings bindings) {
        // ... validate

        if (context == null) {
            throw new IllegalArgumentException("Invalid SCXML context");
        }

        if (bindings == null) {
            throw new IllegalArgumentException("Invalid script Bindings");
        }

        // ... initialise

        this.bindings = bindings;
        this.context = context;
    }

    // INSTANCE METHODS

    /**
     * Returns <code>true</code> if the wrapped Bindings delegate
     * or SCXML context  contains a variable identified by
     * <code>key</code>.
     */

    public boolean containsKey(Object key) {
        if (hasGlobalBindings() && getGlobalBindings().containsKey(key)) {
            return true;
        }

        if (bindings.containsKey(key)) {
            return true;
        }

        return context.has(key.toString());
    }

    /**
     * Returns a union of the wrapped Bindings entry set and the
     * SCXML context entry set.
     * <p/>
     * NOTE: doesn't seem to be invoked ever. Not thread-safe.
     */

    public Set<String> keySet() {
        Set<String> keys = new HashSet<String>();

        keys.addAll(context.getVars().keySet());
        keys.addAll(bindings.keySet());

        if (hasGlobalBindings()) {
            keys.addAll(getGlobalBindings().keySet());
        }

        return keys;
    }

    /**
     * Returns the combined size of the wrapped Bindings entry set and the
     * SCXML context entry set.
     * <p/>
     * NOTE: doesn't seem to be invoked ever so not sure if it works in
     * context. Not thread-safe.
     */

    public int size() {
        Set<String> keys = new HashSet<String>();

        keys.addAll(context.getVars().keySet());
        keys.addAll(bindings.keySet());

        if (hasGlobalBindings()) {
            keys.addAll(getGlobalBindings().keySet());
        }

        return keys.size();
    }

    /**
     * Returns <code>true</code> if the wrapped Bindings delegate
     * or SCXML context contains <code>value</code>.
     * <p/>
     * NOTE: doesn't seem to be invoked ever so not sure if it works in
     * context. Not thread-safe.
     */

    public boolean containsValue(Object value) {
        if (hasGlobalBindings() && getGlobalBindings().containsValue(value)) {
            return true;
        }

        if (bindings.containsValue(value)) {
            return true;
        }

        return context.getVars().containsValue(value);
    }

    /**
     * Returns a union of the wrapped Bindings entry set and the
     * SCXML context entry set.
     * <p/>
     * NOTE: doesn't seem to be invoked ever so not sure if it works in
     * context. Not thread-safe.
     */

    public Set<Entry<String, Object>> entrySet() {
        return union().entrySet();
    }

    /**
     * Returns a union of the wrapped Bindings value list and the
     * SCXML context value list.
     * <p/>
     * NOTE: doesn't seem to be invoked ever so not sure if it works in
     * context. Not thread-safe.
     */

    public Collection<Object> values() {
        return union().values();
    }

    /**
     * Returns a <code>true</code> if both the Bindings delegate and
     * the SCXML context maps are empty.
     * <p/>
     * NOTE: doesn't seem to be invoked ever so not sure if it works in
     * context. Not thread-safe.
     */

    public boolean isEmpty() {
        if (hasGlobalBindings() && !getGlobalBindings().isEmpty()) {
            return false;
        }

        if (!bindings.isEmpty()) {
            return false;
        }

        return context.getVars().isEmpty();
    }

    /**
     * Returns the value from the wrapped Bindings delegate
     * or SCXML context contains identified by <code>key</code>.
     */

    public Object get(Object key) {
        // nashorn.global should be retrieved from the bindings, not from context.
        if (NASHORN_GLOBAL.equals(key)) {
            return bindings.get(key);
        }

        if (hasGlobalBindings() && getGlobalBindings().containsKey(key)) {
            return getGlobalBindings().get(key);
        }

        if (bindings.containsKey(key)) {
            return bindings.get(key);
        }

        return context.get(key.toString());
    }

    /**
     * The following delegation model is used to set values:
     * <ol>
     * <li>Delegates to {@link Context#set(String, Object)} if the
     * {@link Context} contains the key (name), else</li>
     * <li>Delegates to the wrapped {@link Bindings#put(String, Object)}
     * if the {@link Bindings} contains the key (name), else</li>
     * <li>Delegates to {@link Context#setLocal(String, Object)}</li>
     * </ol>
     */

    public Object put(String name, Object value) {
        Object old = context.get(name);

        // nashorn.global should be put into the bindings, not into context.
        if (NASHORN_GLOBAL.equals(name)) {
            return bindings.put(name, value);
        } else if (context.has(name)) {
            context.set(name, value);
        } else if (bindings.containsKey(name)) {
            return bindings.put(name, value);
        } else if (hasGlobalBindings() && getGlobalBindings().containsKey(name)) {
            return getGlobalBindings().put(name, value);
        } else {
            context.setLocal(name, value);
        }

        return old;
    }

    /**
     * Delegates to the wrapped Bindings <code>putAll</code> method i.e. does
     * not store variables in the SCXML context.
     * <p/>
     * NOTE: doesn't seem to be invoked ever so not sure if it works in
     * context. Not thread-safe.
     */

    public void putAll(Map<? extends String, ? extends Object> toMerge) {
        bindings.putAll(toMerge);
    }

    /**
     * Removes the object from the wrapped Bindings instance or the contained
     * SCXML context. Not entirely sure about this implementation but it
     * follows the philosophy of using the Javascript Bindings as a child context
     * of the SCXML context.
     * <p/>
     * NOTE: doesn't seem to be invoked ever so not sure if it works in
     * context. Not thread-safe.
     */

    public Object remove(Object key) {
        if (hasGlobalBindings() && getGlobalBindings().containsKey(key)) {
            getGlobalBindings().remove(key);
        }

        if (bindings.containsKey(key)) {
            return bindings.remove(key);
        }

        if (context.has(key.toString())) {
            return context.getVars().remove(key);
        }

        return Boolean.FALSE;
    }

    /**
     * Delegates to the wrapped Bindings <code>clear</code> method. Does not clear
     * the SCXML context.
     * <p/>
     * NOTE: doesn't seem to be invoked ever so not sure if it works in
     * context. Not thread-safe.
     */

    public void clear() {
        bindings.clear();
    }

    /**
     * Internal method to create a union of the SCXML context and the Javascript
     * Bindings. Does a heavyweight copy - and so far only invoked by the
     * not used methods.
     */
    private Bindings union() {
        Bindings set = new SimpleBindings();

        set.putAll(context.getVars());

        for (String key : bindings.keySet()) {
            set.put(key, bindings.get(key));
        }

        if (hasGlobalBindings()) {
            for (String key : getGlobalBindings().keySet()) {
                set.put(key, getGlobalBindings().get(key));
            }
        }

        return set;
    }

    /**
     * Return true if a global bindings (i.e. nashorn Global instance) was ever set by the script engine.
     * <p>
     * Note: because the global binding can be set by the script engine when evaluating a script, we should
     * check or retrieve the global binding whenever needed instead of initialization time.
     * </p>
     *
     * @return true if a global bindings (i.e. nashorn Global instance) was ever set by the script engine
     */
    protected boolean hasGlobalBindings() {
        return bindings.containsKey(NASHORN_GLOBAL);

    }

    /**
     * Return the global bindings (i.e. nashorn Global instance) set by the script engine if existing.
     *
     * @return the global bindings (i.e. nashorn Global instance) set by the script engine, or null if not existing.
     */
    protected Bindings getGlobalBindings() {
        if (bindings.containsKey(NASHORN_GLOBAL)) {
            return (Bindings) bindings.get(NASHORN_GLOBAL);
        }

        return null;
    }
}
