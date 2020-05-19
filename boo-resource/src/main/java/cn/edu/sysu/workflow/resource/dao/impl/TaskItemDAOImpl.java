package cn.edu.sysu.workflow.resource.dao.impl;

import cn.edu.sysu.workflow.common.entity.TaskItem;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.resource.dao.TaskItemDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * {@link TaskItemDAO}
 *
 * @author Skye
 * Created on 2020/3/18
 */
@Repository
public class TaskItemDAOImpl implements TaskItemDAO {

    private static final Logger log = LoggerFactory.getLogger(TaskItemDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TaskItem findOne(String taskItemId) {
        String sql = "SELECT task_item_id, business_object_id, task_polymorphism_id, task_polymorphism_name, " +
                "business_role, principle, event_descriptor, hook_descriptor, documentation, parameters " +
                "FROM boo_task_item " +
                "WHERE task_item_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{taskItemId}, (RowMapper<TaskItem>) (resultSet, i) -> {
                TaskItem taskItem = new TaskItem();
                taskItem.setTaskItemId(resultSet.getString("task_item_id"));
                taskItem.setBusinessObjectId(resultSet.getString("business_object_id"));
                taskItem.setTaskPolymorphismId(resultSet.getString("task_polymorphism_id"));
                taskItem.setTaskPolymorphismName(resultSet.getString("task_polymorphism_name"));
                taskItem.setBusinessRole(resultSet.getString("business_role"));
                taskItem.setPrinciple(resultSet.getString("principle"));
                taskItem.setEventDescriptor(resultSet.getString("event_descriptor"));
                taskItem.setHookDescriptor(resultSet.getString("hook_descriptor"));
                taskItem.setDocumentation(resultSet.getString("documentation"));
                taskItem.setParameters(resultSet.getString("parameters"));
                return taskItem;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + taskItemId + "]Error on querying task item by taskItemId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public TaskItem findByPIIdAndBONAndTaskName(String processInstanceId, String businessObjectName, String taskName) {
        String sql = "SELECT bti.task_item_id, bti.business_object_id, bti.task_polymorphism_id, bti.task_polymorphism_name, " +
                "bti.business_role, bti.principle, bti.event_descriptor, bti.hook_descriptor, bti.documentation, bti.parameters " +
                "FROM boo_task_item bti , boo_process_instance bpi, boo_business_object bbo " +
                "WHERE bpi.process_instance_id = ? AND bpi.process_id = bbo.process_id AND bbo.business_object_name = ? " +
                "AND bbo.business_object_id= bti.business_object_id AND bti.task_polymorphism_name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{processInstanceId, businessObjectName, taskName}, (RowMapper<TaskItem>) (resultSet, i) -> {
                TaskItem taskItem = new TaskItem();
                taskItem.setTaskItemId(resultSet.getString("task_item_id"));
                taskItem.setBusinessObjectId(resultSet.getString("business_object_id"));
                taskItem.setTaskPolymorphismId(resultSet.getString("task_polymorphism_id"));
                taskItem.setTaskPolymorphismName(resultSet.getString("task_polymorphism_name"));
                taskItem.setBusinessRole(resultSet.getString("business_role"));
                taskItem.setPrinciple(resultSet.getString("principle"));
                taskItem.setEventDescriptor(resultSet.getString("event_descriptor"));
                taskItem.setHookDescriptor(resultSet.getString("hook_descriptor"));
                taskItem.setDocumentation(resultSet.getString("documentation"));
                taskItem.setParameters(resultSet.getString("parameters"));
                return taskItem;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + processInstanceId + "::" + businessObjectName + "::" + taskName +
                    "]Error on querying task item by processInstanceId, businessObjectName and taskName.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public TaskItem findByPIIdAndBOIdAndTaskId(String processInstanceId, String businessObjectId, String taskPolymorphismId) {
        String sql = "SELECT bti.task_item_id, bti.business_object_id, bti.task_polymorphism_id, bti.task_polymorphism_name, " +
                "bti.business_role, bti.principle, bti.event_descriptor, bti.hook_descriptor, bti.documentation, bti.parameters " +
                "FROM boo_task_item bti , boo_process_instance bpi, boo_business_object bbo " +
                "WHERE bpi.process_instance_id = ? AND bpi.process_id = bbo.process_id AND bbo.business_object_id = ? " +
                "AND bbo.business_object_id= bti.business_object_id AND task_polymorphism_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{processInstanceId, businessObjectId, taskPolymorphismId}, (RowMapper<TaskItem>) (resultSet, i) -> {
                TaskItem taskItem = new TaskItem();
                taskItem.setTaskItemId(resultSet.getString("task_item_id"));
                taskItem.setBusinessObjectId(resultSet.getString("business_object_id"));
                taskItem.setTaskPolymorphismId(resultSet.getString("task_polymorphism_id"));
                taskItem.setTaskPolymorphismName(resultSet.getString("task_polymorphism_name"));
                taskItem.setBusinessRole(resultSet.getString("business_role"));
                taskItem.setPrinciple(resultSet.getString("principle"));
                taskItem.setEventDescriptor(resultSet.getString("event_descriptor"));
                taskItem.setHookDescriptor(resultSet.getString("hook_descriptor"));
                taskItem.setDocumentation(resultSet.getString("documentation"));
                taskItem.setParameters(resultSet.getString("parameters"));
                return taskItem;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + processInstanceId + "::" + businessObjectId + "::" + taskPolymorphismId +
                    "]Error on querying task item by processInstanceId, businessObjectId and taskPolymorphismId.", e);
            throw new DAOException(e);
        }
    }
}
