package cn.edu.sysu.workflow.engine.core;

import java.io.Serializable;

/**
 * A class representing an event. Specific event types have been
 * defined in reference to SCXML.
 * <p/>
 * <b>NOTE:</b> Instances are {@link Serializable} as long as the associated
 * payload, if any, is {@link Serializable}.
 */
public class TriggerEvent implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param name    The event name
     * @param type    The event type
     * @param payload The event payload, must be {@link Serializable}
     */
    public TriggerEvent(final String name, final int type,
                        final Object payload) {
        super();
        this.name = name != null ? name.trim() : "";
        this.type = type;
        this.payload = payload;
    }

    /**
     * Constructor.
     *
     * @param name The event name
     * @param type The event type
     */
    public TriggerEvent(final String name, final int type) {
        this(name, type, null);
    }

    /**
     * <code>CALL_EVENT</code>.
     */
    public static final int CALL_EVENT = 1;

    /**
     * <code>CHANGE_EVENT</code>.
     */
    public static final int CHANGE_EVENT = 2;

    /**
     * <code>SIGNAL_EVENT</code>.
     */
    public static final int SIGNAL_EVENT = 3;

    /**
     * <code>TIME_EVENT</code>.
     */
    public static final int TIME_EVENT = 4;

    /**
     * <code>ERROR_EVENT</code>.
     */
    public static final int ERROR_EVENT = 5;

    /**
     * <code>CANCEL_EVENT</code>.
     */
    public static final int CANCEL_EVENT = 6;

    /**
     * The predefined SCXML 'error.execution' Event name
     * <p>
     * Indicates that an error internal to the execution of the document has occurred, such as one arising from
     * expression evaluation.
     * </p>
     *
     * @see <a href="http://www.w3.org/TR/scxml/#errorsAndEvents">
     * http://www.w3.org/TR/scxml/#errorsAndEvents</a>
     */
    public static final String ERROR_EXECUTION = "error.execution";

    /**
     * The predefined SCXML 'error.communication' Event name
     * <p>
     * Indicates that an error has occurred while trying to communicate with an external context.
     * </p>
     *
     * @see <a href="http://www.w3.org/TR/scxml/#errorsAndEvents">
     * http://www.w3.org/TR/scxml/#errorsAndEvents</a>
     */
    public static final String ERROR_COMMUNICATION = "error.communication";

    /**
     * The predefined SCXML 'error.platform' Event name
     * <p>
     * Indicates that a platform- or application-specific error has occurred.
     * </p>
     *
     * @see <a href="http://www.w3.org/TR/scxml/#errorsAndEvents">
     * http://www.w3.org/TR/scxml/#errorsAndEvents</a>
     */
    public static final String ERROR_PLATFORM = "error.platform";

    /**
     * The event name.
     */
    private String name;

    /**
     * The event type.
     */
    private int type;

    /**
     * The event payload.
     */
    private Object payload;

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Returns the payload.
     */
    public Object getPayload() {
        return payload;
    }

    /**
     * @return Returns the type.
     */
    public int getType() {
        return type;
    }

    /**
     * Define an equals operator for TriggerEvent.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TriggerEvent) {
            TriggerEvent te2 = (TriggerEvent) obj;
            return type == te2.type && name.equals(te2.name)
                    && ((payload == null && te2.payload == null)
                    || (payload != null && payload.equals(te2.payload)));
        }
        return false;
    }

    /**
     * Returns a string representation of this TriggerEvent object.
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer("TriggerEvent{name=");
        buf.append(name).append(",type=").append(type);
        if (payload != null) {
            buf.append(",payload=").append(payload.toString());
        }
        buf.append("}");
        return String.valueOf(buf);
    }

    /**
     * Returns the hash code for this TriggerEvent object.
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return String.valueOf(this).hashCode();
    }

}

