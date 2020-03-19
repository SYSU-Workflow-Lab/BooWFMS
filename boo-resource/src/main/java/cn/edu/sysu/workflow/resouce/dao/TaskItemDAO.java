package cn.edu.sysu.workflow.resouce.dao;

import cn.edu.sysu.workflow.common.entity.TaskItem;

/**
 * TaskItem数据库操作
 *
 * @author Skye
 * Created on 2020/3/17
 */
public interface TaskItemDAO {

    /**
     * Get a task item by its id
     *
     * @param taskItemId
     * @return
     */
    TaskItem findOne(String taskItemId);

    /**
     * Get a task item by its name and belonging BO name of one process instance.
     *
     * @param processInstanceId     process instance id
     * @param businessObjectName   belong to BO name
     * @param taskName task polymorphism name
     * @return Task resourcing context, null if exception occurred or assertion error
     */
    TaskItem findByPIIDAndBONAndTaskName(String processInstanceId, String businessObjectName, String taskName);

    /**
     * Get a task item by its name and belonging BO name of one process instance.
     *
     * @param processInstanceId     process instance id
     * @param businessObjectId   belong to BO id
     * @param taskPolymorphismId task polymorphism id
     * @return Task resourcing context, null if exception occurred or assertion error
     */
    TaskItem findByPIIdAndBOIdAndTaskId(String processInstanceId, String businessObjectId, String taskPolymorphismId);

}
