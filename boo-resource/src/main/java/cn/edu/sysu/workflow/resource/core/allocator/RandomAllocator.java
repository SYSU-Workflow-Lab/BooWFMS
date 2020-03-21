package cn.edu.sysu.workflow.resource.core.allocator;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.resource.core.context.WorkItemContext;

import java.util.*;

/**
 * Author: Rinkako
 * Date  : 2018/2/13
 * Usage : Performs allocation based on random choose.
 */
public class RandomAllocator extends Allocator {

    /**
     * Allocator description.
     */
    public static final String Descriptor = "The random allocator chooses " +
            "from a distribution set the participant randomly.";

    /**
     * Constructor for reflect.
     */
    public RandomAllocator() {
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
    public RandomAllocator(String id, String type, HashMap<String, String> args) {
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
        int chosenIdx = new Random().nextInt(candidateSet.size());
        return (ProcessParticipant) candidateSet.toArray()[chosenIdx];
    }
}
