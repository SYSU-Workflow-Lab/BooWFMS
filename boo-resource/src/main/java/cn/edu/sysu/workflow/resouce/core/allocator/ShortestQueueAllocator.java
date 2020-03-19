package cn.edu.sysu.workflow.resouce.core.allocator;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.enums.WorkItemListType;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resouce.service.WorkItemListItemService;
import cn.edu.sysu.workflow.resouce.service.WorkItemListService;
import cn.edu.sysu.workflow.resouce.util.SpringContextUtil;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Rinkako
 * Date  : 2018/2/9
 * Usage : Performs allocation based on work queue length, prefer shortest.
 */
public class ShortestQueueAllocator extends Allocator {

    /**
     * Allocator description.
     */
    public static final String Descriptor = "The Shortest-Queue allocator " +
            "chooses from a distribution set the participant with the " +
            "shortest Allocated queue (i.e. the least number of work items " +
            "in the Allocated queue).";

    /**
     * Constructor for reflect.
     */
    public ShortestQueueAllocator() {
        this("Allocator_" + UUID.randomUUID().toString(), ShortestQueueAllocator.class.getName(), null);
    }

    /**
     * Create a new allocator.
     * Allocator should not be created directly, use {@code AllocateInteractionExecutor} instead.
     *
     * @param id   unique id for selector fetching
     * @param type type name string
     * @param args parameter dictionary in HashMap
     */
    public ShortestQueueAllocator(String id, String type, HashMap<String, String> args) {
        super(id, type, ShortestQueueAllocator.Descriptor, args);
    }

    /**
     * Perform allocation on the candidate set.
     *
     * @param candidateSet candidate participant set
     * @param context      work item context
     * @return selected participant
     */
    @Override
    public ProcessParticipant performAllocate(Set<ProcessParticipant> candidateSet, WorkItemContext context) {
        int currentShortest = Integer.MAX_VALUE;
        int currentLength;
        ProcessParticipant retCtx = null;
        WorkItemListService workItemListService = (WorkItemListService) SpringContextUtil.getBean("workItemListServiceImpl");
        for (ProcessParticipant p : candidateSet) {
            if (workItemListService.isNullOrEmptyWorkItemList(p.getAccountId(), WorkItemListType.ALLOCATED)) {
                return p;
            } else {
                WorkItemListItemService workItemListItemService = (WorkItemListItemService) SpringContextUtil.getBean("workItemListItemServiceImpl");
                currentLength = workItemListItemService.count(workItemListService.getWorkItemList(p.getAccountId(), WorkItemListType.ALLOCATED));
            }
            if (currentLength < currentShortest) {
                currentShortest = currentLength;
                retCtx = p;
            }
        }
        return retCtx;
    }
}
