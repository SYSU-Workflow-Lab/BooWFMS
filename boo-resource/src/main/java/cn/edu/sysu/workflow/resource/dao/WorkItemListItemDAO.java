package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.WorkItemListItem;

import java.util.List;

/**
 * WorkItemListItem数据库操作
 *
 * @author Skye
 * Created on 2020/3/14
 */
public interface WorkItemListItemDAO {

    /**
     * 创建工作项列表项
     *
     * @param workItemListItem <ul>
     *                         <li>WorkItemListItemId</li>
     *                         <li>WorkItemListId</li>
     *                         <li>workItemId</li>
     *                         </ul>
     * @return 新增的数据量
     */
    int save(WorkItemListItem workItemListItem);

    /**
     * 更新工作项列表项，不允许更新作项列表项Id
     *
     * @param workItemListItem <ul>
     *                        <li>WorkItemListItemId</li>
     *                        <li>WorkItemListId</li>
     *                        <li>workItemId</li>
     *                        </ul>
     * @return 修改的数据量
     */
    int update(WorkItemListItem workItemListItem);

    /**
     * 根据工作项列表Id和工作项Id删除工作项列表项
     *
     * @param workItemListId
     * @param workItemId
     * @return 修改的数据量
     */
    int deleteByWorkItemListIdAndWorkItemId(String workItemListId, String workItemId);

    /**
     * 根据工作项Id删除工作项列表项
     *
     * @param workItemId
     * @return 修改的数据量
     */
    int deleteByWorkItemId(String workItemId);

    /**
     * 根据工作项列表Id查找工作项列表项数据列表
     *
     * @param workItemListId
     * @return
     */
    List<WorkItemListItem> findWorkItemListItemsByWorkItemListId(String workItemListId);

    /**
     * 根据工作项Id查找工作项列表项数据列表
     *
     * @param workItemId
     * @return
     */
    List<WorkItemListItem> findWorkItemListItemsByWorkItemId(String workItemId);

    /**
     * 根据工作项列表Id查找工作项列表项数据
     *
     * @param workItemListId
     * @param workItemId
     * @return
     */
    WorkItemListItem findByWorkItemListIdAndWorkItemId(String workItemListId, String workItemId);

}
