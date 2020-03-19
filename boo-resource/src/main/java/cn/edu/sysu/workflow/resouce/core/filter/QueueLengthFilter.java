package cn.edu.sysu.workflow.resouce.core.filter;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.entity.WorkItemList;
import cn.edu.sysu.workflow.common.enums.WorkItemListType;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resouce.core.evaluator.RJexlEvaluator;
import cn.edu.sysu.workflow.resouce.core.evaluator.RJexlMapContext;
import cn.edu.sysu.workflow.resouce.service.WorkItemListItemService;
import cn.edu.sysu.workflow.resouce.service.WorkItemListService;
import cn.edu.sysu.workflow.resouce.util.SpringContextUtil;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;

import java.util.*;

/**
 * Author: Rinkako
 * Date  : 2018/2/9
 * Usage : Filters a distribution set based on length of Work Item List.
 */
public class QueueLengthFilter extends Filter {

    /**
     * Filter description.
     */
    public static final String Descriptor = "The QueueLength filter do " +
            "will apply a condition expression to check if participant " +
            " offered queue length satisfied the condition.";

    /**
     * Parameter name of queue length in condition expression.
     */
    private static final String ParameterLength = "length";

    /**
     * Condition evaluable instance.
     */
    private Expression conditionExprObj;

    /**
     * Constructor for reflect.
     */
    public QueueLengthFilter() {
        this("Filter_" + UUID.randomUUID().toString(), QueueLengthFilter.class.getName(), null);
    }

    /**
     * Create a new filter.
     *
     * @param id   unique id for selector fetching
     * @param type type name string
     * @param args parameter dictionary in HashMap
     */
    public QueueLengthFilter(String id, String type, HashMap<String, String> args) {
        super(id, type, QueueLengthFilter.Descriptor, args);
    }

    /**
     * Apply principle to configure selector.
     */
    @Override
    protected void applyPrinciple() {
        Map distributorArgs = this.principle.getDistributorArgsMap();
        this.setCondition(String.format("%s %s", QueueLengthFilter.ParameterLength, distributorArgs.get(QueueLengthFilter.ParameterLength)));
    }

    /**
     * Set the filter condition.
     * Expression should contain variable `length` for getting queue length.
     * A valid expression example: "length lt 10" means queue length less than 10
     *
     * @param conditionExpr condition expression in JLex
     */
    private void setCondition(String conditionExpr) {
        this.conditionExprObj = RJexlEvaluator.CommonEngine.createExpression(conditionExpr);
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
        HashSet<ProcessParticipant> retSet = new HashSet<>();
        RJexlMapContext tmpCtx = new RJexlMapContext();
        WorkItemListService workItemListService = (WorkItemListService) SpringContextUtil.getBean("workItemListServiceImpl");
        for (ProcessParticipant p : candidateSet) {
            WorkItemList offered = workItemListService.getWorkItemList(p.getAccountId(), WorkItemListType.OFFERED);
            WorkItemListItemService workItemListItemService = (WorkItemListItemService) SpringContextUtil.getBean("workItemListItemServiceImpl");
            int queueLen = offered == null ? 0 : workItemListItemService.count(offered);
            tmpCtx.Add(QueueLengthFilter.ParameterLength, queueLen);
            Object condResult = this.conditionExprObj.evaluate((JexlContext) tmpCtx.GetInternalContext());
            if (condResult == null ? Boolean.FALSE : (Boolean) condResult) {
                retSet.add(p);
            }
        }
        return retSet;
    }
}
