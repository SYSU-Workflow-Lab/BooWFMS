package cn.edu.sysu.workflow.resouce.core.executor;


import cn.edu.sysu.workflow.common.enums.InitializationType;

/**
 * Author: Rinkako
 * Date  : 2018/2/4
 * Usage : Base class for Offer, Allocate and Start interaction points.
 */
public abstract class InteractionExecutor {

    /**
     * Level of initiator.
     */
    protected InitializationType initiator = InitializationType.USER_INITIATED;

    /**
     * Belong to Task global id.
     */
    protected String ownerTaskId;

    /**
     * Create a new Interaction executor.
     */
    public InteractionExecutor() {
    }

    /**
     * Create a new Interaction executor.
     *
     * @param ownerTaskId belong to task global id
     * @param type        initiator type
     */
    public InteractionExecutor(String ownerTaskId, InitializationType type) {
        this.initiator = type;
        this.ownerTaskId = ownerTaskId;
    }

    /**
     * Get the initiator type.
     *
     * @return initiator type enum
     */
    public InitializationType getInitiatorType() {
        return this.initiator;
    }

    /**
     * Get the owner task global id.
     *
     * @return task gid string
     */
    public String getOwnerTaskId() {
        return this.ownerTaskId;
    }
}
