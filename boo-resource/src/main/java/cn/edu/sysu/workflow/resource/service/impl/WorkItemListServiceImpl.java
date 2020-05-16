package cn.edu.sysu.workflow.resource.service.impl;

import cn.edu.sysu.workflow.common.entity.WorkItem;
import cn.edu.sysu.workflow.common.entity.WorkItemList;
import cn.edu.sysu.workflow.common.entity.WorkItemListItem;
import cn.edu.sysu.workflow.common.enums.WorkItemListType;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.resource.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resource.dao.WorkItemDAO;
import cn.edu.sysu.workflow.resource.dao.WorkItemListDAO;
import cn.edu.sysu.workflow.resource.service.TaskItemContextService;
import cn.edu.sysu.workflow.resource.service.WorkItemListItemService;
import cn.edu.sysu.workflow.resource.service.WorkItemListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link WorkItemListService}
 *
 * @author Skye
 * Created on 2020/3/17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorkItemListServiceImpl implements WorkItemListService {

    private static final Logger log = LoggerFactory.getLogger(WorkItemListServiceImpl.class);

    @Autowired
    private WorkItemListItemService workItemListItemService;

    @Autowired
    private WorkItemListDAO workItemListDAO;

    @Autowired
    private WorkItemDAO workItemDAO;

    @Autowired
    private TaskItemContextService taskItemContextService;

    @Override
    public WorkItemList findByOwnerAccountIdAndType(String ownerAccountId, WorkItemListType type) {
        try {
            WorkItemList workItemList = workItemListDAO.findByOwnerAccountIdAndType(ownerAccountId, type.ordinal());
            // if not exist in entity then create a new one
            if (workItemList == null) {
                workItemList = new WorkItemList();
                workItemList.setWorkItemListId(WorkItemList.PREFIX + IdUtil.nextId());
                workItemList.setOwnerAccountId(ownerAccountId);
                workItemList.setType(type.ordinal());
                workItemListDAO.save(workItemList);
            }
            return workItemList;
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(String.format("Get WorkQueueContext (owner: %s, type: %s) exception occurred, %s",
                    ownerAccountId, type.name(), ex));
            throw ex;
        }
    }

    @Override
    public void moveFromOfferedToAllocated(WorkItemContext workItemContext, String ownerAccountId) {
        this.move(workItemContext, ownerAccountId, WorkItemListType.OFFERED, WorkItemListType.ALLOCATED);
    }

    @Override
    public void moveFromAllocatedToOffered(WorkItemContext workItemContext, String ownerAccountId) {
        this.move(workItemContext, ownerAccountId, WorkItemListType.ALLOCATED, WorkItemListType.OFFERED);
    }

    @Override
    public void moveFromOfferedToStarted(WorkItemContext workItemContext, String ownerAccountId) {
        this.move(workItemContext, ownerAccountId, WorkItemListType.OFFERED, WorkItemListType.STARTED);
    }

    @Override
    public void moveFromStartedToOffered(WorkItemContext workItemContext, String ownerAccountId) {
        this.move(workItemContext, ownerAccountId, WorkItemListType.STARTED, WorkItemListType.OFFERED);
    }

    @Override
    public void moveFromAllocatedToStarted(WorkItemContext workItemContext, String ownerAccountId) {
        this.move(workItemContext, ownerAccountId, WorkItemListType.ALLOCATED, WorkItemListType.STARTED);
    }

    @Override
    public void moveFromStartedToAllocated(WorkItemContext workItemContext, String ownerAccountId) {
        this.move(workItemContext, ownerAccountId, WorkItemListType.STARTED, WorkItemListType.ALLOCATED);
    }

    @Override
    public void moveFromStartedToSuspend(WorkItemContext workItemContext, String ownerAccountId) {
        this.move(workItemContext, ownerAccountId, WorkItemListType.STARTED, WorkItemListType.SUSPENDED);
    }

    @Override
    public void moveFromSuspendToStarted(WorkItemContext workItemContext, String ownerAccountId) {
        this.move(workItemContext, ownerAccountId, WorkItemListType.SUSPENDED, WorkItemListType.STARTED);
    }

    /**
     * Move a work item from a work item list to another.
     * NOTICE that this method usually should NOT be called outside, since not all move is valid.
     *
     * @param workItemContext work item context to be moved
     * @param ownerAccountId  工作项列表所有者账户Id
     * @param from            from work item list type
     * @param to              to work item list type
     */
    private void move(WorkItemContext workItemContext, String ownerAccountId, WorkItemListType from, WorkItemListType to) {
        this.removeFromWorkItemList(workItemContext, ownerAccountId, from);
        this.addToWorkItemList(workItemContext, ownerAccountId, to);
    }

    @Override
    public void addToWorkItemList(WorkItemContext workItemContext, String ownerAccountId, WorkItemListType type) {
        workItemListItemService.addOrUpdate(this.getWorkItemList(ownerAccountId, type), workItemContext);
    }

    @Override
    public void removeFromWorkItemList(WorkItemContext workItemContext, String ownerAccountId, WorkItemListType type) {
        workItemListItemService.removeByWorkItemContext(this.getWorkItemList(ownerAccountId, type), workItemContext);
    }

    @Override
    public List<WorkItemContext> getWorkItemListItems(String ownerAccountId, WorkItemListType type) {
        List<WorkItemListItem> workItemListItems =
                workItemListItemService.findWorkItemListItemsByWorkItemListId(this.getWorkItemList(ownerAccountId, type));
        List<WorkItemContext> workItemContexts = new ArrayList<>();
        for (WorkItemListItem i : workItemListItems) {
            WorkItemContext workItemContext = new WorkItemContext();
            WorkItem workItem = workItemDAO.findOne(i.getWorkItemId());
            workItemContext.setWorkItem(workItem);
            workItemContext.setTaskItemContext(taskItemContextService.getContext(workItem));
            workItemContext.setArgsDict(JsonUtil.jsonDeserialization(workItem.getArguments(), HashMap.class));
            workItemContexts.add(workItemContext);
        }
        return workItemContexts;
    }

    @Override
    public List<WorkItemContext> getWorkListedWorkItems(String ownerAccountId) {
        List<WorkItemContext> workItemContexts = new ArrayList<>();
        WorkItemList workListed = this.getWorkItemList(ownerAccountId, WorkItemListType.WORKLISTED);
        for (int qType = WorkItemListType.OFFERED.ordinal(); qType <= WorkItemListType.SUSPENDED.ordinal(); qType++) {
            workItemContexts.addAll(this.getWorkItemListItems(ownerAccountId, WorkItemListType.values()[qType]));
            workItemListItemService.addAnotherWorkItemList(workListed, this.getWorkItemList(ownerAccountId, WorkItemListType.values()[qType]));
        }
        return workItemContexts;
    }

    @Override
    public boolean isNullOrEmptyWorkItemList(String ownerAccountId, WorkItemListType type) {
        WorkItemList wq = this.getWorkItemList(ownerAccountId, type);
        return wq == null || workItemListItemService.isEmpty(wq);
    }

    @Override
    public WorkItemList getWorkItemList(String ownerAccountId, WorkItemListType type) {
        return this.findByOwnerAccountIdAndType(ownerAccountId, type);
    }
}
