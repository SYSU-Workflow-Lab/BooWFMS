package cn.edu.sysu.workflow.resouce.core.trigger;

import java.util.List;

/**
 * Author: Rinkako
 * Date  : 2018/2/5
 * Usage : Immediate trigger for executor.
 */
public class ImmediateTrigger extends Trigger {

    /**
     * Trigger payload package.
     */
    private Object payload;

    /**
     * Create an immediate trigger.
     *
     * @param id       trigger unique id
     * @param listener object to be triggered
     * @param payload  payload for trigger
     */
    public ImmediateTrigger(String id, Triggerable listener, Object payload) {
        // empty condition for immediate trigger
        super(id, "");
        this.payload = payload;
        this.AddTriggerable(listener);
    }

    /**
     * Create an immediate trigger.
     *
     * @param id           trigger unique id
     * @param listenerList list of objects to be triggered
     * @param payload      payload for trigger
     */
    public ImmediateTrigger(String id, List<Triggerable> listenerList, Object payload) {
        // empty condition for immediate trigger
        super(id, "");
        this.payload = payload;
        for (Triggerable triggerable : listenerList) {
            this.AddTriggerable(triggerable);
        }
    }

    /**
     * Actually run the trigger.
     * Must be implemented in delivered class.
     */
    @Override
    protected void ActualRun() {
        this.RunOnce(this.payload);
    }

    /**
     * Actually stop the trigger.
     * Must be implemented in delivered class.
     */
    @Override
    protected void ActualStop() {
        // nothing
    }
}
