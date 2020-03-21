package cn.edu.sysu.workflow.resource.core.trigger;

/**
 * Author: Rinkako
 * Date  : 2018/2/5
 * Usage : Interface for class which can be triggered by Trigger.
 */
public interface Triggerable {
    /**
     * This method will be called when the Triggerable object
     * was triggered by its binding trigger.
     *
     * @param trigger Trigger instance.
     * @param payload Triggering payload package
     */
    void Triggered(Trigger trigger, Object payload);
}
