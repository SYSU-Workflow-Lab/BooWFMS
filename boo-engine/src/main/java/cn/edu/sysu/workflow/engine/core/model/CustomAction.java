package cn.edu.sysu.workflow.engine.core.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A custom action is simply a tuple consisting of a namespace URI,
 * the local name for the custom action and the corresponding
 * {@link Action} class.
 */
public class CustomAction {

    /**
     * Error logged while attempting to define a custom action
     * in a null or empty namespace.
     */
    private static final String ERR_NO_NAMESPACE =
            "Cannot define a custom SCXML action with a null or empty namespace";

    /**
     * The SCXML namespace, to which custom actions may not be added.
     */
    private static final String NAMESPACE_SCXML =
            "http://www.w3.org/2005/07/scxml";

    /**
     * Error logged while attempting to define a custom action
     * with the SCXML namespace.
     */
    private static final String ERR_RESERVED_NAMESPACE =
            "Cannot define a custom SCXML action within the SCXML namespace '"
                    + NAMESPACE_SCXML + "'";

    /**
     * Error logged while attempting to define a custom action
     * in a null or empty local name.
     */
    private static final String ERR_NO_LOCAL_NAME =
            "Cannot define a custom SCXML action with a null or empty local name";

    /**
     * Error logged while attempting to define a custom action
     * which does not extend {@link Action}.
     */
    private static final String ERR_NOT_AN_ACTION =
            "Custom SCXML action does not extend Action superclass";

    /**
     * The namespace this custom action belongs to.
     */
    private String namespaceURI;

    /**
     * The local name of the custom action.
     */
    private String localName;

    /**
     * The implementation of this custom action.
     */
    private Class<? extends Action> actionClass;

    /**
     * Constructor, if the namespace or local name is null or empty,
     * or if the implementation is not an {@link Action}, an
     * {@link IllegalArgumentException} is thrown.
     *
     * @param namespaceURI The namespace URI for this custom action.
     * @param localName    The local name for this custom action.
     * @param actionClass  The {@link Action} subclass implementing this
     *                     custom action.
     */
    public CustomAction(final String namespaceURI, final String localName,
                        final Class<? extends Action> actionClass) {
        Log log = LogFactory.getLog(CustomAction.class);
        if (namespaceURI == null || namespaceURI.trim().length() == 0) {
            log.error(ERR_NO_NAMESPACE);
            throw new IllegalArgumentException(ERR_NO_NAMESPACE);
        }
        if (namespaceURI.trim().equalsIgnoreCase(NAMESPACE_SCXML)) {
            log.error(ERR_RESERVED_NAMESPACE);
            throw new IllegalArgumentException(ERR_RESERVED_NAMESPACE);
        }
        if (localName == null || localName.trim().length() == 0) {
            log.error(ERR_NO_LOCAL_NAME);
            throw new IllegalArgumentException(ERR_NO_LOCAL_NAME);
        }
        if (actionClass == null || !Action.class.isAssignableFrom(actionClass)) {
            log.error(ERR_NOT_AN_ACTION);
            throw new IllegalArgumentException(ERR_NOT_AN_ACTION);
        }
        this.namespaceURI = namespaceURI;
        this.localName = localName;
        this.actionClass = actionClass;
    }

    /**
     * Get this custom action's implementation.
     *
     * @return Returns the action class.
     */
    public Class<? extends Action> getActionClass() {
        return actionClass;
    }

    /**
     * Get the local name for this custom action.
     *
     * @return Returns the local name.
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Get the namespace URI for this custom action.
     *
     * @return Returns the namespace URI.
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }
}

