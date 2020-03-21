package cn.edu.sysu.workflow.resource.core.trigger;

import cn.edu.sysu.workflow.common.util.TimestampUtil;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Author: Rinkako
 * Date  : 2018/2/5
 * Usage : Base class for triggers. Trigger is used to fire work item or
 * execute other resource service in a specific condition.
 */
public abstract class Trigger implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Trigger unique id for fetching.
     */
    private String id;

    /**
     * Trigger enable flag.
     */
    private boolean active;

    /**
     * Trigger condition expression.
     */
    private String triggerCondition;

    /**
     * Timestamp of last begin this trigger.
     */
    private Timestamp lastBeginTimestamp;

    /**
     * Timestamp of last stop this trigger.
     */
    private Timestamp lastStopTimestamp;

    /**
     * Timestamp of last time this trigger successfully triggered anything.
     */
    private Timestamp lastTriggerTimestamp;

    /**
     * Trigger binding Triggerable object.
     */
    private ArrayList<Triggerable> triggerList = new ArrayList<>();

    /**
     * Create a new trigger.
     */
    public Trigger() {
    }

    /**
     * Create a new trigger.
     *
     * @param id trigger id
     */
    public Trigger(String id, String condition) {
        this.id = id;
        this.triggerCondition = condition;
    }

    /**
     * Add triggerable object.
     *
     * @param triggerable Triggerable object.
     */
    public void AddTriggerable(Triggerable triggerable) {
        this.triggerList.add(triggerable);
    }

    /**
     * Clear all triggerable object.
     */
    public void Clear() {
        this.triggerList.clear();
    }

    /**
     * Get if this trigger is active.
     *
     * @return boolean of active status
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Set this trigger active status.
     *
     * @param active destination status
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get the trigger condition expression.
     *
     * @return condition string
     */
    public String getTriggerCondition() {
        return this.triggerCondition;
    }

    /**
     * Get the unique id of trigger.
     *
     * @return id string
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get timestamp of last begin this trigger.
     *
     * @return timestamp
     */
    public Timestamp getLastBeginTimestamp() {
        return lastBeginTimestamp;
    }

    /**
     * Get timestamp of last stop this trigger.
     *
     * @return timestamp
     */
    public Timestamp getLastStopTimestamp() {
        return lastStopTimestamp;
    }

    /**
     * Get timestamp of last time this trigger successfully triggered anything.
     *
     * @return timestamp
     */
    public Timestamp getLastTriggerTimestamp() {
        return lastTriggerTimestamp;
    }

    /**
     * Begin the trigger.
     */
    public final void Run() {
        this.active = true;
        this.lastBeginTimestamp = TimestampUtil.getCurrentTimestamp();
        this.ActualRun();
    }

    /**
     * Stop the trigger.
     */
    public final void Stop() {
        this.ActualStop();
        this.lastStopTimestamp = TimestampUtil.getCurrentTimestamp();
        this.active = false;
    }

    /**
     * Signal that the trigger has successfully triggered anything.
     */
    protected final void SuccessfullyTriggered() {
        this.lastTriggerTimestamp = TimestampUtil.getCurrentTimestamp();
    }

    /**
     * Actually run the trigger.
     * Must be implemented in delivered class.
     */
    protected abstract void ActualRun();

    /**
     * Actually stop the trigger.
     * Must be implemented in delivered class.
     */
    protected abstract void ActualStop();

    /**
     * Start the trigger once.
     *
     * @param payload payload package to be sent to triggered object.
     * @return triggered Triggerable object count
     */
    public int RunOnce(Object payload) {
        boolean checkResult = Trigger.EvaluateCondition(this.triggerCondition);
        if (checkResult) {
            for (Triggerable triggerable : this.triggerList) {
                triggerable.Triggered(this, payload);
            }
            this.SuccessfullyTriggered();
        }
        return checkResult ? this.triggerList.size() : 0;
    }

    /**
     * Evaluate a trigger condition expression.
     *
     * @param expr condition string
     * @return trigger condition calculated result
     */
    public static boolean EvaluateCondition(String expr) {
        if (StringUtils.isEmpty(expr)) {
            return true;
        }
        // todo other situations
        return true;
    }
}
