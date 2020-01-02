package cn.edu.sysu.workflow.engine.dao;

import cn.edu.sysu.workflow.common.entity.TaskItem;

/**
 * 用于模型解析任务数据库操作
 *
 * @author Skye
 * Created on 2020/1/2
 */
public interface TaskItemDAO {

    /**
     * 创建流程实例单步数据快照
     *
     * @param taskItem <ul>
     *                 <li>taskItemId</li>
     *                 <li>businessObjectId</li>
     *                 <li>taskPolymorphismId</li>
     *                 <li>taskPolymorphismName</li>
     *                 <li>businessRole</li>
     *                 <li>principle</li>
     *                 <li>eventDescriptor</li>
     *                 <li>hookDescriptor</li>
     *                 <li>documentation</li>
     *                 <li>parameters</li>
     *                 </ul>
     * @return 新增的数据量
     */
    int save(TaskItem taskItem);

}
