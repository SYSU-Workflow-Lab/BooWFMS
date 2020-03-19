package cn.edu.sysu.workflow.resouce.core.filter;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.resouce.core.Selector;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Rinkako
 * Date  : 2018/2/2
 * Usage : Base allocator for all implemented filters.
 * Filter is used to remove participants in candidate set who cannot map the filter conditions.
 */
public abstract class Filter extends Selector implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create a new filter.
     *
     * @param id          unique id for selector fetching
     * @param type        type name string
     * @param description selector description text
     * @param args        parameter dictionary in HashMap
     */
    public Filter(String id, String type, String description, HashMap<String, String> args) {
        super(id, type, description, args);
    }

    /**
     * Perform filter on the candidate set.
     *
     * @param candidateSet candidate participant set
     * @param context      work item request context
     * @return filtered participant set
     */
    public abstract HashSet<ProcessParticipant> performFilter(Set<ProcessParticipant> candidateSet, WorkItemContext context);
}
