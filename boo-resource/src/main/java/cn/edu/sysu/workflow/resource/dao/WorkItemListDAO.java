package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.WorkItemList;

/**
 * @author Skye
 * Created on 2020/3/17
 */
public interface WorkItemListDAO {

    /**
     * 创建工作项列表
     *
     * @param workItemList <ul>
     *                         <li>workItemListId</li>
     *                         <li>ownerAccountId</li>
     *                         <li>type</li>
     *                         </ul>
     * @return 新增的数据量
     */
    int save(WorkItemList workItemList);

    /**
     * 根据工作项列表所有者Id和工作项列表类型查找工作项列表数据
     *
     * @param ownerAccountId
     * @param type
     * @return
     */
    WorkItemList findByOwnerAccountIdAndType(String ownerAccountId, Integer type);

}
