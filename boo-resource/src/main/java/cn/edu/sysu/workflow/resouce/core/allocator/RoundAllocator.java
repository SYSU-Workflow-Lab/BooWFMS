package cn.edu.sysu.workflow.resouce.core.allocator;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Rinkako
 * Date  : 2018/3/8
 * Usage : Performs allocation on rounding choose.
 */
public class RoundAllocator extends Allocator {

    /**
     * Allocator description.
     */
    public static final String Descriptor = "The round allocator is special allocator. It will " +
            "allocate the work item to a participant never receive this type of wor kitem.";

    /**
     * Constructor for reflect.
     */
    public RoundAllocator() {
        this("Allocator_" + UUID.randomUUID().toString(), RandomAllocator.class.getName(), null);
    }

    /**
     * Create a new allocator.
     * Allocator should not be created directly, use {@code AllocateInteractionExecutor} instead.
     *
     * @param id   unique id for selector fetching
     * @param type type name string
     * @param args parameter dictionary in HashMap
     */
    public RoundAllocator(String id, String type, HashMap<String, String> args) {
        super(id, type, RandomAllocator.Descriptor, args);
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
        synchronized (RoundAllocator.roundingSetMutex) {
            HashSet<String> roundSet = roundingCloseSet.computeIfAbsent(
                    String.format("%s_%s", context.getTaskItemContext().getTaskGlobalId(), context.getWorkItem().getProcessInstanceId()),
                    k -> new HashSet<>());
            for (ProcessParticipant participant : candidateSet) {
                if (!roundSet.contains(participant.getAccountId())) {
                    roundSet.add(participant.getAccountId());
                    return participant;
                }
            }
            // round but no one satisfied, re round
            roundSet.clear();
            ProcessParticipant reRounded = candidateSet.iterator().next();
            roundSet.add(reRounded.getAccountId());
            return reRounded;
        }
    }

    /**
     * Rounding set, pattern (taskGlobalId_processInstanceId, Set of allocated workerId).
     */
    private static final HashMap<String, HashSet<String>> roundingCloseSet = new HashMap<>();

    /**
     * Mutex for rounding set.
     */
    private static final Object roundingSetMutex = new Object();
}
