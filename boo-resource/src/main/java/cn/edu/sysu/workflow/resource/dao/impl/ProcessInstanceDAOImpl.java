package cn.edu.sysu.workflow.resource.dao.impl;

import cn.edu.sysu.workflow.common.entity.ProcessInstance;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.resource.dao.ProcessInstanceDAO;
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

/**
 * {@link ProcessInstanceDAO}
 *
 * @author Skye
 * Created on 2019/12/29
 */
@Repository
public class ProcessInstanceDAOImpl implements ProcessInstanceDAO {

    private static final Logger log = LoggerFactory.getLogger(ProcessInstanceDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int update(ProcessInstance processInstance) {
        String sql = "UPDATE boo_process_instance SET last_update_timestamp = NOW()";
        // processId
        if (!StringUtils.isEmpty(processInstance.getProcessId())) {
            sql += ", process_id = ?";
        }
        // launchAccountId
        if (!StringUtils.isEmpty(processInstance.getLaunchAccountId())) {
            sql += ", launch_account_id = ?";
        }
        // launchMethod
        if (!StringUtils.isEmpty(processInstance.getLaunchMethod())) {
            sql += ", launch_method = ?";
        }
        // launchType
        if (null != processInstance.getLaunchType()) {
            sql += ", launch_type = ?";
        }
        // engineId
        if (!StringUtils.isEmpty(processInstance.getEngineId())) {
            sql += ", engine_id = ?";
        }
        // resourceServiceId
        if (!StringUtils.isEmpty(processInstance.getResourceServiceId())) {
            sql += ", resource_service_id = ?";
        }
        // resourceBinding
        if (!StringUtils.isEmpty(processInstance.getResourceBinding())) {
            sql += ", resource_binding = ?";
        }
        // resourceBindingType
        if (null != processInstance.getResourceBindingType()) {
            sql += ", resource_binding_type = ?";
        }
        // failureType
        if (null != processInstance.getFailureType()) {
            sql += ", failure_type = ?";
        }
        // participantCache
        if (!StringUtils.isEmpty(processInstance.getParticipantCache())) {
            sql += ", participant_cache = ?";
        }
        // launchTimestamp
        if (null != processInstance.getLaunchTimestamp()) {
            sql += ", launch_timestamp = ?";
        }
        // finishTimestamp
        if (null != processInstance.getFinishTimestamp()) {
            sql += ", finish_timestamp = ?";
        }
        // resultType
        if (null != processInstance.getResultType()) {
            sql += ", result_type = ?";
        }
        // tag
        if (!StringUtils.isEmpty(processInstance.getTag())) {
            sql += ", tag = ?";
        }
        // processInstanceId
        sql += " WHERE process_instance_id = ?";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // processId
                    if (!StringUtils.isEmpty(processInstance.getProcessId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getProcessId(), Types.VARCHAR);
                    }
                    // launchAccountId
                    if (!StringUtils.isEmpty(processInstance.getLaunchAccountId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getLaunchAccountId(), Types.VARCHAR);
                    }
                    // launchMethod
                    if (!StringUtils.isEmpty(processInstance.getLaunchMethod())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getLaunchMethod(), Types.VARCHAR);
                    }
                    // launchType
                    if (null != processInstance.getLaunchType()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getLaunchType(), Types.INTEGER);
                    }
                    // engineId
                    if (!StringUtils.isEmpty(processInstance.getEngineId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getEngineId(), Types.VARCHAR);
                    }
                    // resourceServiceId
                    if (!StringUtils.isEmpty(processInstance.getResourceServiceId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getResourceServiceId(), Types.VARCHAR);
                    }
                    // resourceBinding
                    if (!StringUtils.isEmpty(processInstance.getResourceBinding())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getResourceBinding(), Types.VARCHAR);
                    }
                    // resourceBindingType
                    if (null != processInstance.getResourceBindingType()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getResourceBindingType(), Types.INTEGER);
                    }
                    // failureType
                    if (null != processInstance.getFailureType()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getFailureType(), Types.INTEGER);
                    }
                    // participantCache
                    if (!StringUtils.isEmpty(processInstance.getParticipantCache())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getParticipantCache(), Types.VARCHAR);
                    }
                    // launchTimestamp
                    if (null != processInstance.getLaunchTimestamp()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getLaunchTimestamp(), Types.TIMESTAMP);
                    }
                    // finishTimestamp
                    if (null != processInstance.getFinishTimestamp()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getFinishTimestamp(), Types.TIMESTAMP);
                    }
                    // resultType
                    if (null != processInstance.getResultType()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getResultType(), Types.INTEGER);
                    }
                    // tag
                    if (!StringUtils.isEmpty(processInstance.getTag())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getTag(), Types.VARCHAR);
                    }
                    // processInstanceId
                    JdbcUtil.preparedStatementSetter(ps, index(), processInstance.getProcessInstanceId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + processInstance.getProcessInstanceId() + "]Error on updating process instance by processInstanceId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public ProcessInstance findOne(String processInstanceId) {
        String sql = "SELECT process_instance_id, process_id, launch_account_id, launch_method, launch_type, engine_id, " +
                "resource_service_id, resource_binding, resource_binding_type, failure_type, participant_cache, launch_timestamp, " +
                "finish_timestamp, result_type, tag " +
                "FROM boo_process_instance " +
                "WHERE process_instance_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{processInstanceId}, new RowMapper<ProcessInstance>() {
                @Override
                public ProcessInstance mapRow(ResultSet resultSet, int i) throws SQLException {
                    ProcessInstance processInstance = new ProcessInstance();
                    processInstance.setProcessInstanceId(resultSet.getString("process_instance_id"));
                    processInstance.setProcessId(resultSet.getString("process_id"));
                    processInstance.setLaunchAccountId(resultSet.getString("launch_account_id"));
                    processInstance.setLaunchMethod(resultSet.getString("launch_method"));
                    processInstance.setLaunchType(resultSet.getInt("launch_type"));
                    processInstance.setEngineId(resultSet.getString("engine_id"));
                    processInstance.setResourceServiceId(resultSet.getString("resource_service_id"));
                    processInstance.setResourceBinding(resultSet.getString("resource_binding"));
                    processInstance.setResourceBindingType(resultSet.getInt("resource_binding_type"));
                    processInstance.setFailureType(resultSet.getInt("failure_type"));
                    processInstance.setParticipantCache(resultSet.getString("participant_cache"));
                    processInstance.setLaunchTimestamp(resultSet.getTimestamp("launch_timestamp"));
                    processInstance.setFinishTimestamp(resultSet.getTimestamp("finish_timestamp"));
                    processInstance.setResultType(resultSet.getInt("result_type"));
                    processInstance.setTag(resultSet.getString("tag"));
                    return processInstance;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + processInstanceId + "]Error on querying process instance by processInstanceId.", e);
            throw new DAOException(e);
        }
    }

}
