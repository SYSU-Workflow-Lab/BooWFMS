package cn.edu.sysu.workflow.resource.core.api;

import cn.edu.sysu.workflow.common.entity.ExitItem;
import cn.edu.sysu.workflow.common.enums.FailedWorkItemStatus;
import cn.edu.sysu.workflow.common.enums.FailedWorkItemVisibilityType;
import cn.edu.sysu.workflow.common.enums.ResourceEventType;
import cn.edu.sysu.workflow.common.util.AuthDomainHelper;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.resource.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resource.dao.ExitItemDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Author: Rinkako
 * Date  : 2018/2/9
 * Usage : Implementation of Interface X of Resource Service.
 * Interface X is responsible for process exception handling.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class InterfaceX {

    private static final Logger log = LoggerFactory.getLogger(InterfaceX.class);

    @Autowired
    private ExitItemDAO exitItemDAO;

    /**
     * Signal a work item is failed, and redirect it to its admin launcher exception work item pool.
     *
     * @param workItemContext failed work item
     * @param reason   failed reason
     */
    public void failedRedirectToLauncherDomainPool(WorkItemContext workItemContext, String reason) {
        log.info("Work item[" + workItemContext.getWorkItem().getWorkItemId() + "] is at the stage of " + ResourceEventType.exception_lifecycle.name() +".");
        this.routeFailedWorkItem(workItemContext, reason, FailedWorkItemVisibilityType.DomainOnly);
    }

    /**
     * Signal a work item which principle parsing failed, and redirect it to its admin launcher exception work item pool.
     *
     * @param workItemContext failed work item
     */
    public void principleParseFailedRedirectToDomainPool(WorkItemContext workItemContext) {
        log.info("Work item[" + workItemContext.getWorkItem().getWorkItemId() + "] is at the stage of " + ResourceEventType.exception_principle.name() +".");
        this.routeFailedWorkItem(workItemContext, "Principle Parse Failed.", FailedWorkItemVisibilityType.DomainOnly);
    }

    /**
     * Route a work item to exception pool.
     *
     * @param workItemContext failed work item
     * @param reason   reason of failure
     * @param visibility
     */
    public void routeFailedWorkItem(WorkItemContext workItemContext, String reason, FailedWorkItemVisibilityType visibility) {
        try {
            ExitItem exitItem = new ExitItem();
            exitItem.setExitItemId(ExitItem.PREFIX + IdUtil.nextId());
            exitItem.setProcessInstanceId(workItemContext.getWorkItem().getProcessInstanceId());
            exitItem.setWorkItemId(workItemContext.getWorkItem().getWorkItemId());
            exitItem.setReason(reason);
            exitItem.setStatus(FailedWorkItemStatus.Unhandled.ordinal());
            exitItem.setVisibility(visibility.ordinal());
            exitItemDAO.save(exitItem);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        this.notifyException(workItemContext);
    }

    /**
     * Handle fast fail of a process instance.
     *
     * @param processInstanceId process instance id
     */
    public void handleFastFail(String processInstanceId) {
        // todo
    }

    /**
     * Notify the auth user about exception happened by notify its binding hook URL.
     *
     * @param workItemContext failed work item
     */
    public void notifyException(WorkItemContext workItemContext) {
        String launcher = AuthDomainHelper.getAuthNameByProcessInstanceId(workItemContext.getWorkItem().getProcessInstanceId());
        // todo here do notification.
    }
}
