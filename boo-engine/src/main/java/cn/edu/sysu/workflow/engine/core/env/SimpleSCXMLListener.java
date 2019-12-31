package cn.edu.sysu.workflow.engine.core.env;

import cn.edu.sysu.workflow.engine.core.SCXMLListener;
import cn.edu.sysu.workflow.engine.core.model.EnterableState;
import cn.edu.sysu.workflow.engine.core.model.Transition;
import cn.edu.sysu.workflow.engine.core.model.TransitionTarget;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;


/**
 * Simple SCXML Listener that logs execution.
 * <p/>
 * 记录执行过程中日志，的SCXMLListener
 */
public class SimpleSCXMLListener implements SCXMLListener, Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Log.
     */
    private Log log = LogFactory.getLog(getClass());


    /**
     * @see SCXMLListener#onEntry(EnterableState)
     */
    public void onEntry(final EnterableState state) {
        if (log.isInfoEnabled()) {
            log.info("enter " + LogUtils.getTTPath(state));
        }
    }

    /**
     * @see SCXMLListener#onExit(EnterableState)
     */
    public void onExit(final EnterableState state) {
        if (log.isInfoEnabled()) {
            log.info("exit " + LogUtils.getTTPath(state));
        }
    }

    /**
     * @see SCXMLListener#onTransition(TransitionTarget, TransitionTarget, Transition, String)
     */
    public void onTransition(final TransitionTarget from,
                             final TransitionTarget to, final Transition transition, String event) {
        if (log.isInfoEnabled()) {
            log.info("transition " + LogUtils.transToString(from, to, transition, event));
        }
    }

}

