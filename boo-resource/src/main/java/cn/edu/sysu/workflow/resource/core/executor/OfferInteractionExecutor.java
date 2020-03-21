package cn.edu.sysu.workflow.resource.core.executor;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.enums.InitializationType;
import cn.edu.sysu.workflow.resource.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resource.core.filter.Filter;
import cn.edu.sysu.workflow.resource.core.principle.Principle;
import cn.edu.sysu.workflow.resource.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Author: Rinkako
 * Date  : 2018/2/4
 * Usage :
 */
public class OfferInteractionExecutor extends InteractionExecutor {

    private static final Logger log = LoggerFactory.getLogger(OfferInteractionExecutor.class);

    /**
     * Create a new offer interaction executor.
     *
     * @param ownerTaskId id of task to create this
     * @param type        type of service invoker type
     */
    public OfferInteractionExecutor(String ownerTaskId, InitializationType type) {
        super(ownerTaskId, type);
    }

    /**
     * Binding allocator.
     */
    private Filter filter;

    /**
     * Get the binding filter.
     *
     * @return get filter instance.
     */
    public Filter getFilter() {
        return this.filter;
    }

    /**
     * Use allocator to handle allocation service.
     *
     * @return Filtered Participant context set.
     */
    public Set<ProcessParticipant> performOffer(Set<ProcessParticipant> candidateSet, WorkItemContext workItemContext) {
        if (this.filter == null) {
            log.error("Perform offer before binding executor to a filter.");
            return null;
        }
        return this.filter.performFilter(candidateSet, workItemContext);
    }

    /**
     * Binding a allocator to this executor by the principle of task.
     *
     * @param principle allocation principle
     */
    public void bindingFilter(Principle principle, String processInstanceId) throws Exception {
        this.filter = ReflectUtil.reflectFilter(principle.getDistributorName(), processInstanceId);
        this.filter.bindingPrinciple(principle);
    }
}
