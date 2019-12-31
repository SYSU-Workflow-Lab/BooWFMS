package cn.edu.sysu.workflow.engine.core.env;

import cn.edu.sysu.workflow.engine.core.model.Transition;
import cn.edu.sysu.workflow.engine.core.model.TransitionTarget;

/**
 * Helper methods for Commons SCXML logging.
 */
public final class LogUtils {

    /**
     * Create a human readable log view of this transition.
     *
     * @param from       The source TransitionTarget
     * @param to         The destination TransitionTarget
     * @param transition The Transition that is taken
     * @param event      The event name triggering the transition
     * @return String The human readable log entry
     */
    public static String transToString(final TransitionTarget from,
                                       final TransitionTarget to, final Transition transition, String event) {
        StringBuffer buf = new StringBuffer("(");
        buf.append("event = ").append(event);
        buf.append(", cond = ").append(transition.getCond());
        buf.append(", from = ").append(getTTPath(from));
        buf.append(", to = ").append(getTTPath(to));
        buf.append(')');
        return buf.toString();
    }

    /**
     * Write out this TransitionTarget location in a XPath style format.
     *
     * @param tt The TransitionTarget whose &quot;path&quot; is to needed
     * @return String The XPath style location of the TransitionTarget within
     * the SCXML document
     */
    public static String getTTPath(final TransitionTarget tt) {
        StringBuilder sb = new StringBuilder("/");
        for (int i = 0; i < tt.getNumberOfAncestors(); i++) {
            sb.append(tt.getAncestor(i).getId());
            sb.append("/");
        }
        sb.append(tt.getId());
        return sb.toString();
    }

    /**
     * Discourage instantiation since this is a utility class.
     */
    private LogUtils() {
        super();
    }

}
