package cn.edu.sysu.workflow.resouce.service.impl;

import cn.edu.sysu.workflow.common.entity.WorkItemList;
import cn.edu.sysu.workflow.common.entity.WorkItemListItem;
import cn.edu.sysu.workflow.common.enums.WorkItemListType;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.resouce.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resouce.dao.WorkItemDAO;
import cn.edu.sysu.workflow.resouce.dao.WorkItemListItemDAO;
import cn.edu.sysu.workflow.resouce.service.WorkItemListItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * {@link WorkItemListItemService}
 *
 * @author Skye
 * Created on 2020/3/17
 */
@Service
public class WorkItemListItemServiceImpl implements WorkItemListItemService {

    private static final Logger log = LoggerFactory.getLogger(WorkItemListItemServiceImpl.class);

    @Autowired
    private WorkItemListItemDAO workItemListItemDAO;

    @Autowired
    private WorkItemDAO workItemDAO;

    @Override
    public void addOrUpdate(WorkItemList workItemList, WorkItemContext workItemContext) {
        WorkItemListItem workItemListItem =
                workItemListItemDAO.findByWorkItemListIdAndWorkItemId(
                        workItemList.getWorkItemListId(), workItemContext.getWorkItem().getWorkItemId());
        if (workItemListItem == null) {
            workItemListItem = new WorkItemListItem();
            workItemListItem.setWorkItemListItemId(WorkItemListItem.PREFIX + IdUtil.nextId());
            workItemListItem.setWorkItemListId(workItemList.getWorkItemListId());
            workItemListItem.setWorkItemId(workItemContext.getWorkItem().getWorkItemId());
            workItemListItemDAO.save(workItemListItem);
        } else {
            workItemListItem.setWorkItemListId(workItemList.getWorkItemListId());
            workItemListItem.setWorkItemId(workItemContext.getWorkItem().getWorkItemId());
            workItemListItemDAO.update(workItemListItem);
        }
        log.info("Worker[" + workItemList.getOwnerAccountId() + "] adds the work item[" + workItemContext.getWorkItem().getWorkItemId()
                + "] at the stage of " + WorkItemListType.values()[workItemList.getType()].name() + ".");
    }

    @Override
    public void addAnotherWorkItemList(WorkItemList workItemList, WorkItemList another) {
        List<WorkItemListItem> anotherWorkItemListItems =
                workItemListItemDAO.findWorkItemListItemsByWorkItemListId(another.getWorkItemListId());
        for (WorkItemListItem i : anotherWorkItemListItems) {
            WorkItemListItem workItemListItem = workItemListItemDAO.findByWorkItemListIdAndWorkItemId(
                    workItemList.getWorkItemListId(), i.getWorkItemId());
            if (workItemListItem == null) {
                workItemListItem = new WorkItemListItem();
                workItemListItem.setWorkItemListItemId(WorkItemListItem.PREFIX + IdUtil.nextId());
                workItemListItem.setWorkItemListId(workItemList.getWorkItemListId());
                workItemListItem.setWorkItemId(i.getWorkItemId());
                workItemListItemDAO.save(workItemListItem);
            } else {
                workItemListItem.setWorkItemListId(workItemList.getWorkItemListId());
                workItemListItem.setWorkItemId(i.getWorkItemId());
                workItemListItemDAO.update(workItemListItem);
            }
            log.info("Worker[" + workItemList.getOwnerAccountId() + "] adds the work item[" + i.getWorkItemId()
                    + "] at the stage of " + WorkItemListType.values()[workItemList.getType()].name() + ".");
        }
    }

    @Override
    public void removeByWorkItemContext(WorkItemList workItemList, WorkItemContext workItemContext) {
        workItemListItemDAO.deleteByWorkItemListIdAndWorkItemId(workItemList.getWorkItemListId(), workItemContext.getWorkItem().getWorkItemId());
    }

    @Override
    public void removeByProcessInstanceId(WorkItemList workItemList, String processInstanceId) {
        List<WorkItemListItem> workItemListItems =
                workItemListItemDAO.findWorkItemListItemsByWorkItemListId(workItemList.getWorkItemListId());
        for (WorkItemListItem i : workItemListItems) {
            if (processInstanceId.equals(workItemDAO.findProcessInstanceIdByWorkItemId(i.getWorkItemId()))) {
                workItemListItemDAO.deleteByWorkItemListIdAndWorkItemId(workItemList.getWorkItemListId(), i.getWorkItemId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByWorkItemId(WorkItemContext workItemContext) {

        if (workItemContext == null) {
            return;
        }
        try {
            workItemListItemDAO.deleteByWorkItemId(workItemContext.getWorkItem().getWorkItemId());
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(String.format("[%s]When RemoveFromAllQueue(%s) refresh from entity exception occurred, %s",
                    workItemContext.getWorkItem().getProcessInstanceId(), workItemContext.getWorkItem().getWorkItemId(), ex));
        }
    }

    @Override
    public boolean isEmpty(WorkItemList workItemList) {
        return workItemListItemDAO.findWorkItemListItemsByWorkItemListId(workItemList.getWorkItemListId()).isEmpty();
    }

    @Override
    public int count(WorkItemList workItemList) {
        return workItemListItemDAO.findWorkItemListItemsByWorkItemListId(workItemList.getWorkItemListId()).size();
    }

    @Override
    public boolean contains(WorkItemList workItemList, String workItemId) {
        return workItemListItemDAO.findByWorkItemListIdAndWorkItemId(workItemList.getWorkItemListId(), workItemId) != null;
    }

    @Override
    public WorkItemListItem retrieve(WorkItemList workItemList, String workItemId) {
        return workItemListItemDAO.findByWorkItemListIdAndWorkItemId(workItemList.getWorkItemListId(), workItemId);
    }

    @Override
    public List<WorkItemListItem> findWorkItemListItemsByWorkItemListId(WorkItemList workItemList) {
        return workItemListItemDAO.findWorkItemListItemsByWorkItemListId(workItemList.getWorkItemListId());
    }

}
