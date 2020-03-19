package cn.edu.sysu.workflow.resouce.core.executor;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.enums.InitializationType;
import cn.edu.sysu.workflow.resouce.core.allocator.Allocator;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resouce.core.principle.Principle;
import cn.edu.sysu.workflow.resouce.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Author: Rinkako
 * Date  : 2018/2/4
 * Usage : This class is responsible for handle resources allocation phase.
 */
public class AllocateInteractionExecutor extends InteractionExecutor {

    private static final Logger log = LoggerFactory.getLogger(AllocateInteractionExecutor.class);

    /**
     * Binding allocator.
     */
    private Allocator allocator;

    /**
     * Create a new allocate interaction executor.
     *
     * @param ownerTaskId id of task to create this
     * @param type        type of service invoker type
     */
    public AllocateInteractionExecutor(String ownerTaskId, InitializationType type) {
        super(ownerTaskId, type);
    }

    /**
     * Get the binding allocator.
     *
     * @return get allocator instance.
     */
    public Allocator getAllocator() {
        return this.allocator;
    }

    /**
     * Use allocator to handle allocation service.
     *
     * @param candidateSet           candidate participant set
     * @param workItemContext work item request context
     * @return Allocated Participant context.
     */
    public ProcessParticipant performAllocation(Set<ProcessParticipant> candidateSet, WorkItemContext workItemContext) {
        if (this.allocator == null) {
            log.error("[" + workItemContext.getWorkItem().getProcessInstanceId()
                    + "]Perform allocation before binding executor to an allocator.");
            return null;
        }
        return this.allocator.performAllocate(candidateSet, workItemContext);
    }

    /**
     * Binding a allocator to this executor by the principle of task.
     *
     * @param principle allocation principle
     */
    public void bindingAllocator(Principle principle, String processInstanceId) throws Exception {
        this.allocator = ReflectUtil.reflectAllocator(principle.getDistributorName(), processInstanceId);
        this.allocator.bindingPrinciple(principle);
    }
}
