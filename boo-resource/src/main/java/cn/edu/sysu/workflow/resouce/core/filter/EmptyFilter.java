package cn.edu.sysu.workflow.resouce.core.filter;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Author: Rinkako
 * Date  : 2018/2/9
 * Usage : Filters a distribution set by doing nothing.
 */
public class EmptyFilter extends Filter {

    /**
     * Filter description.
     */
    public static final String Descriptor = "The Empty filter do " +
            "nothing to a candidate set, return it as nothing happened.";

    /**
     * Constructor for reflect.
     */
    public EmptyFilter() {
        this("Filter_" + UUID.randomUUID().toString(), EmptyFilter.class.getName(), null);
    }

    /**
     * Apply principle to configure selector.
     */
    @Override
    protected void applyPrinciple() {
        // nothing
    }

    /**
     * Create a new filter.
     *
     * @param id   unique id for selector fetching
     * @param type type name string
     * @param args parameter dictionary in HashMap
     */
    public EmptyFilter(String id, String type, HashMap<String, String> args) {
        super(id, type, EmptyFilter.Descriptor, args);
    }

    /**
     * Perform filter on the candidate set.
     *
     * @param candidateSet candidate participant set
     * @param context      work item request context
     * @return filtered participant set
     */
    @Override
    public HashSet<ProcessParticipant> performFilter(Set<ProcessParticipant> candidateSet, WorkItemContext context) {
        return new HashSet<>(candidateSet);
    }
}
