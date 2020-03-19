package cn.edu.sysu.workflow.resouce.core.allocator;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.resouce.core.Selector;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Author: Rinkako
 * Date  : 2017/11/15
 * Usage : Base allocator for all implemented allocators.
 * Allocator is used to choose a participant to handle task from candidate set.
 */
public abstract class Allocator extends Selector implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Parameter name of allocation instance.
     */
    protected static final String ParameterInstanceCount = "instance";

    /**
     * Create a new allocator.
     *
     * @param id          unique id for selector fetching
     * @param type        type name string
     * @param description selector description text
     * @param args        parameter dictionary in HashMap
     */
    public Allocator(String id, String type, String description, HashMap<String, String> args) {
        super(id, type, description, args);
    }

    /**
     * Apply principle to configure selector.
     */
    @Override
    protected void applyPrinciple() {

    }

    /**
     * Perform allocation on the candidate set.
     *
     * @param candidateSet candidate participant set
     * @param context      work item context
     * @return selected participant
     */
    public abstract ProcessParticipant performAllocate(Set<ProcessParticipant> candidateSet, WorkItemContext context);
}
