package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.ArchivedTree;

/**
 * ArchivedTree数据库处理
 *
 * @author Skye
 * Created on 2019/12/31
 */
public interface ArchivedTreeDAO {

    /**
     * 创建ArchivedTree
     *
     * @param archivedTree <ul>
     *                     <li>processInstanceId</li>
     *                     <li>tree</li>
     *                     </ul>
     * @return 新增的数据量
     */
    int save(ArchivedTree archivedTree);

    /**
     * 根据流程实例Id查找ArchivedTree数据库处理数据
     *
     * @param processInstanceId
     * @return
     */
    ArchivedTree findOne(String processInstanceId);
}
