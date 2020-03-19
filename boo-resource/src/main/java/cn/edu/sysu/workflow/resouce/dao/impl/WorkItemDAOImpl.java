package cn.edu.sysu.workflow.resouce.dao.impl;

import cn.edu.sysu.workflow.common.entity.WorkItem;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.resouce.dao.WorkItemDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * {@link WorkItemDAO}
 *
 * @author Skye
 * Created on 2020/3/17
 */
@Repository
public class WorkItemDAOImpl implements WorkItemDAO {

    private static final Logger log = LoggerFactory.getLogger(WorkItemDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(WorkItem workItem) {
        String sql = "INSERT INTO boo_work_item " +
                "(work_item_id, process_instance_id, resource_service_id, process_id, business_object_id, task_id, " +
                "task_polymorphism_id, arguments, allocate_timestamp, launch_timestamp, finish_timestamp, status, " +
                "resourcing_status, launch_account_id, finish_account_id, timer_trigger_id, timer_expiry_id, " +
                "last_launch_timestamp, execute_time, callback_node_id, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // workItemId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getWorkItemId(), Types.VARCHAR);
                    // processInstanceId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getProcessInstanceId(), Types.VARCHAR);
                    // resourceServiceId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getResourceServiceId(), Types.VARCHAR);
                    // processId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getProcessId(), Types.VARCHAR);
                    // businessObjectId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getBusinessObjectId(), Types.VARCHAR);
                    // taskId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getTaskId(), Types.VARCHAR);
                    // taskPolymorphismId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getTaskPolymorphismId(), Types.VARCHAR);
                    // arguments
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getArguments(), Types.VARCHAR);
                    // allocateTimestamp
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getAllocateTimestamp(), Types.VARCHAR);
                    // launchTimestamp
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getLaunchTimestamp(), Types.VARCHAR);
                    // finishTimestamp
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getFinishTimestamp(), Types.VARCHAR);
                    // status
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getStatus(), Types.VARCHAR);
                    // resourcingStatus
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getResourcingStatus(), Types.VARCHAR);
                    // launchAccountId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getLaunchAccountId(), Types.VARCHAR);
                    // FinishAccountId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getFinishAccountId(), Types.VARCHAR);
                    // timerTriggerId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getTimerTriggerId(), Types.VARCHAR);
                    // timeExpiryId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getTimerExpiryId(), Types.VARCHAR);
                    // lastLaunchTimestamp
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getLastLaunchTimestamp(), Types.VARCHAR);
                    // executeTime
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getExecuteTime(), Types.VARCHAR);
                    // callbackNodeId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getCallbackNodeId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + workItem.getWorkItemId() + "]Error on creating work item by workItem.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int update(WorkItem workItem) {
        String sql = "UPDATE boo_work_item SET last_update_timestamp = NOW()";
        // processInstanceId
        if (!StringUtils.isEmpty(workItem.getProcessInstanceId())) {
            sql += ", process_instance_id = ?";
        }
        // resourceServiceId
        if (!StringUtils.isEmpty(workItem.getResourceServiceId())) {
            sql += ", resource_serviec_id = ?";
        }
        // processId
        if (!StringUtils.isEmpty(workItem.getProcessId())) {
            sql += ", process_id = ?";
        }
        // businessObjectId
        if (!StringUtils.isEmpty(workItem.getBusinessObjectId())) {
            sql += ", business_object_id = ?";
        }
        // taskId
        if (!StringUtils.isEmpty(workItem.getTaskId())) {
            sql += ", task_id = ?";
        }
        // taskPolymorphismId
        if (!StringUtils.isEmpty(workItem.getTaskPolymorphismId())) {
            sql += ", task_polymorphism_id = ?";
        }
        // arguments
        if (!StringUtils.isEmpty(workItem.getArguments())) {
            sql += ", arguments = ?";
        }
        // allocateTimestamp
        if (!StringUtils.isEmpty(workItem.getAllocateTimestamp())) {
            sql += ", allocate_timestamp = ?";
        }
        // launchTimestamp
        if (!StringUtils.isEmpty(workItem.getLaunchTimestamp())) {
            sql += ", launch_timestamp = ?";
        }
        // finishTimestamp
        if (!StringUtils.isEmpty(workItem.getFinishTimestamp())) {
            sql += ", finish_timestamp = ?";
        }
        // status
        if (!StringUtils.isEmpty(workItem.getStatus())) {
            sql += ", status = ?";
        }
        // resourcingStatus
        if (!StringUtils.isEmpty(workItem.getResourcingStatus())) {
            sql += ", resourcing_status = ?";
        }
        // launchAccountId
        if (!StringUtils.isEmpty(workItem.getLaunchAccountId())) {
            sql += ", launch_account_id = ?";
        }
        // FinishAccountId
        if (!StringUtils.isEmpty(workItem.getFinishAccountId())) {
            sql += ", finish_account_id = ?";
        }
        // timerTriggerId
        if (!StringUtils.isEmpty(workItem.getTimerTriggerId())) {
            sql += ", timer_trigger_id = ?";
        }
        // timeExpiryId
        if (!StringUtils.isEmpty(workItem.getTimerExpiryId())) {
            sql += ", timer_expiry_id = ?";
        }
        // lastLaunchTimestamp
        if (!StringUtils.isEmpty(workItem.getLastLaunchTimestamp())) {
            sql += ", last_launch_timestamp = ?";
        }
        // executeTime
        if (!StringUtils.isEmpty(workItem.getExecuteTime())) {
            sql += ", execute_time= ?";
        }
        // callbackNodeId
        if (!StringUtils.isEmpty(workItem.getCallbackNodeId())) {
            sql += ", callback_node_id = ?";
        }
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // processInstanceId
                    if (!StringUtils.isEmpty(workItem.getProcessInstanceId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getProcessInstanceId(), Types.VARCHAR);
                    }
                    // resourceServiceId
                    if (!StringUtils.isEmpty(workItem.getResourceServiceId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getResourceServiceId(), Types.VARCHAR);
                    }
                    // processId
                    if (!StringUtils.isEmpty(workItem.getProcessId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getProcessId(), Types.VARCHAR);
                    }
                    // businessObjectId
                    if (!StringUtils.isEmpty(workItem.getBusinessObjectId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getBusinessObjectId(), Types.VARCHAR);
                    }
                    // taskId
                    if (!StringUtils.isEmpty(workItem.getTaskId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getTaskId(), Types.VARCHAR);
                    }
                    // taskPolymorphismId
                    if (!StringUtils.isEmpty(workItem.getTaskPolymorphismId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getTaskPolymorphismId(), Types.VARCHAR);
                    }
                    // arguments
                    if (!StringUtils.isEmpty(workItem.getArguments())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getArguments(), Types.VARCHAR);
                    }
                    // allocateTimestamp
                    if (!StringUtils.isEmpty(workItem.getAllocateTimestamp())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getAllocateTimestamp(), Types.VARCHAR);
                    }
                    // launchTimestamp
                    if (!StringUtils.isEmpty(workItem.getLaunchTimestamp())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getLaunchTimestamp(), Types.VARCHAR);
                    }
                    // finishTimestamp
                    if (!StringUtils.isEmpty(workItem.getFinishTimestamp())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getFinishTimestamp(), Types.VARCHAR);
                    }
                    // status
                    if (!StringUtils.isEmpty(workItem.getStatus())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getStatus(), Types.VARCHAR);
                    }
                    // resourcingStatus
                    if (!StringUtils.isEmpty(workItem.getResourcingStatus())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getResourcingStatus(), Types.VARCHAR);
                    }
                    // launchAccountId
                    if (!StringUtils.isEmpty(workItem.getLaunchAccountId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getLaunchAccountId(), Types.VARCHAR);
                    }
                    // FinishAccountId
                    if (!StringUtils.isEmpty(workItem.getFinishAccountId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getFinishAccountId(), Types.VARCHAR);
                    }
                    // timerTriggerId
                    if (!StringUtils.isEmpty(workItem.getTimerTriggerId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getTimerTriggerId(), Types.VARCHAR);
                    }
                    // timeExpiryId
                    if (!StringUtils.isEmpty(workItem.getTimerExpiryId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getTimerExpiryId(), Types.VARCHAR);
                    }
                    // lastLaunchTimestamp
                    if (!StringUtils.isEmpty(workItem.getLastLaunchTimestamp())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getLastLaunchTimestamp(), Types.VARCHAR);
                    }
                    // executeTime
                    if (!StringUtils.isEmpty(workItem.getExecuteTime())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getExecuteTime(), Types.VARCHAR);
                    }
                    // callbackNodeId
                    if (!StringUtils.isEmpty(workItem.getCallbackNodeId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItem.getCallbackNodeId(), Types.VARCHAR);
                    }
                    // workItemListItemId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItem.getWorkItemId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + workItem.getWorkItemId() + "]Error on updating work item by workItem.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public WorkItem findOne(String workItemId) {
        String sql = "SELECT work_item_id, process_instance_id, resource_service_id, process_id, business_object_id, " +
                "task_id, task_polymorphism_id, arguments, allocate_timestamp, launch_timestamp, finish_timestamp, " +
                "status, resourcing_status, launch_account_id, finish_account_id, timer_trigger_id, timer_expiry_id, " +
                "last_launch_timestamp, execute_time, callback_node_id, create_timestamp " +
                "FROM boo_work_item " +
                "WHERE work_item_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{workItemId}, new RowMapper<WorkItem>() {
                @Override
                public WorkItem mapRow(ResultSet resultSet, int i) throws SQLException {
                    WorkItem workItem = new WorkItem();
                    workItem.setWorkItemId(resultSet.getString("work_item_id"));
                    workItem.setProcessInstanceId(resultSet.getString("process_instance_id"));
                    workItem.setResourceServiceId(resultSet.getString("resource_service_id"));
                    workItem.setProcessId(resultSet.getString("process_id"));
                    workItem.setBusinessObjectId(resultSet.getString("business_object_id"));
                    workItem.setTaskId(resultSet.getString("task_id"));
                    workItem.setTaskPolymorphismId(resultSet.getString("task_polymorphism_id"));
                    workItem.setArguments(resultSet.getString("arguments"));
                    workItem.setAllocateTimestamp(resultSet.getTimestamp("allocate_timestamp"));
                    workItem.setLaunchTimestamp(resultSet.getTimestamp("launch_timestamp"));
                    workItem.setFinishTimestamp(resultSet.getTimestamp("finish_timestamp"));
                    workItem.setStatus(resultSet.getString("status"));
                    workItem.setResourcingStatus(resultSet.getString("resourcing_status"));
                    workItem.setLaunchAccountId(resultSet.getString("launch_account_id"));
                    workItem.setFinishAccountId(resultSet.getString("finish_account_id"));
                    workItem.setTimerTriggerId(resultSet.getString("timer_trigger_id"));
                    workItem.setTimerExpiryId(resultSet.getString("timer_expiry_id"));
                    workItem.setLastLaunchTimestamp(resultSet.getTimestamp("last_launch_timestamp"));
                    workItem.setExecuteTime(resultSet.getLong("execute_time"));
                    workItem.setCallbackNodeId(resultSet.getString("callback_node_id"));
                    workItem.setCreateTimestamp(resultSet.getTimestamp("create_timestamp"));
                    return workItem;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + workItemId + "]Error on querying work item by workItemId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public String findProcessInstanceIdByWorkItemId(String workItemId) {
        String sql = "SELECT process_instance_id " +
                "FROM boo_work_item " +
                "WHERE work_item_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{workItemId}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("process_instance_id");
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + workItemId + "]Error on querying process instance id of work item by workItemId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<WorkItem> findWorkItemsByProcessInstanceId(String processInstanceId) {
        String sql = "SELECT work_item_id, process_instance_id, resource_service_id, process_id, business_object_id, " +
                "task_id, task_polymorphism_id, arguments, allocate_timestamp, launch_timestamp, finish_timestamp, " +
                "status, resourcing_status, launch_account_id, finish_account_id, timer_trigger_id, timer_expiry_id, " +
                "last_launch_timestamp, execute_time, callback_node_id, create_timestamp " +
                "FROM boo_work_item " +
                "WHERE process_instance_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{processInstanceId}, new RowMapper<WorkItem>() {
                @Override
                public WorkItem mapRow(ResultSet resultSet, int i) throws SQLException {
                    WorkItem workItem = new WorkItem();
                    workItem.setWorkItemId(resultSet.getString("work_item_id"));
                    workItem.setProcessInstanceId(resultSet.getString("process_instance_id"));
                    workItem.setResourceServiceId(resultSet.getString("resource_service_id"));
                    workItem.setProcessId(resultSet.getString("process_id"));
                    workItem.setBusinessObjectId(resultSet.getString("business_object_id"));
                    workItem.setTaskId(resultSet.getString("task_id"));
                    workItem.setTaskPolymorphismId(resultSet.getString("task_polymorphism_id"));
                    workItem.setArguments(resultSet.getString("arguments"));
                    workItem.setAllocateTimestamp(resultSet.getTimestamp("allocate_timestamp"));
                    workItem.setLaunchTimestamp(resultSet.getTimestamp("launch_timestamp"));
                    workItem.setFinishTimestamp(resultSet.getTimestamp("finish_timestamp"));
                    workItem.setStatus(resultSet.getString("status"));
                    workItem.setResourcingStatus(resultSet.getString("resourcing_status"));
                    workItem.setLaunchAccountId(resultSet.getString("launch_account_id"));
                    workItem.setFinishAccountId(resultSet.getString("finish_account_id"));
                    workItem.setTimerTriggerId(resultSet.getString("timer_trigger_id"));
                    workItem.setTimerExpiryId(resultSet.getString("timer_expiry_id"));
                    workItem.setLastLaunchTimestamp(resultSet.getTimestamp("last_launch_timestamp"));
                    workItem.setExecuteTime(resultSet.getLong("execute_time"));
                    workItem.setCallbackNodeId(resultSet.getString("callback_node_id"));
                    workItem.setCreateTimestamp(resultSet.getTimestamp("create_timestamp"));
                    return workItem;
                }
            });
        } catch (Exception e) {
            log.error("[" + processInstanceId + "]Error on querying work items by processInstanceId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<WorkItem> findWorkItemsByOrganization(String organization) {
        String sql = "SELECT work_item_id, process_instance_id, resource_service_id, process_id, business_object_id, " +
                "task_id, task_polymorphism_id, arguments, allocate_timestamp, launch_timestamp, finish_timestamp, " +
                "status, resourcing_status, launch_account_id, finish_account_id, timer_trigger_id, timer_expiry_id, " +
                "last_launch_timestamp, execute_time, callback_node_id, create_timestamp " +
                "FROM boo_work_item " +
                "WHERE locate(?, process_instance_id) > 0 = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{organization}, new RowMapper<WorkItem>() {
                @Override
                public WorkItem mapRow(ResultSet resultSet, int i) throws SQLException {
                    WorkItem workItem = new WorkItem();
                    workItem.setWorkItemId(resultSet.getString("work_item_id"));
                    workItem.setProcessInstanceId(resultSet.getString("process_instance_id"));
                    workItem.setResourceServiceId(resultSet.getString("resource_service_id"));
                    workItem.setProcessId(resultSet.getString("process_id"));
                    workItem.setBusinessObjectId(resultSet.getString("business_object_id"));
                    workItem.setTaskId(resultSet.getString("task_id"));
                    workItem.setTaskPolymorphismId(resultSet.getString("task_polymorphism_id"));
                    workItem.setArguments(resultSet.getString("arguments"));
                    workItem.setAllocateTimestamp(resultSet.getTimestamp("allocate_timestamp"));
                    workItem.setLaunchTimestamp(resultSet.getTimestamp("launch_timestamp"));
                    workItem.setFinishTimestamp(resultSet.getTimestamp("finish_timestamp"));
                    workItem.setStatus(resultSet.getString("status"));
                    workItem.setResourcingStatus(resultSet.getString("resourcing_status"));
                    workItem.setLaunchAccountId(resultSet.getString("launch_account_id"));
                    workItem.setFinishAccountId(resultSet.getString("finish_account_id"));
                    workItem.setTimerTriggerId(resultSet.getString("timer_trigger_id"));
                    workItem.setTimerExpiryId(resultSet.getString("timer_expiry_id"));
                    workItem.setLastLaunchTimestamp(resultSet.getTimestamp("last_launch_timestamp"));
                    workItem.setExecuteTime(resultSet.getLong("execute_time"));
                    workItem.setCallbackNodeId(resultSet.getString("callback_node_id"));
                    workItem.setCreateTimestamp(resultSet.getTimestamp("create_timestamp"));
                    return workItem;
                }
            });
        } catch (Exception e) {
            log.error("[" + organization + "]Error on querying work items by organization.", e);
            throw new DAOException(e);
        }
    }
}
