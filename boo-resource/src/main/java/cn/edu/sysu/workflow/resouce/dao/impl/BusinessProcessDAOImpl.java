package cn.edu.sysu.workflow.resouce.dao.impl;

import cn.edu.sysu.workflow.common.entity.BusinessProcess;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.resouce.dao.BusinessProcessDAO;
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
 * {@link BusinessProcessDAO}
 *
 * @author Skye
 * Created on 2020/1/1
 */
@Repository
public class BusinessProcessDAOImpl implements BusinessProcessDAO {

    private static final Logger log = LoggerFactory.getLogger(BusinessProcessDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(BusinessProcess businessProcess) {
        String sql = "INSERT INTO boo_business_process " +
                "(business_process_id, business_process_name, main_business_object_name, creator_id, launch_count, " +
                "success_count, average_cost, status, last_launch_timestamp, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // businessProcessId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getBusinessProcessId(), Types.VARCHAR);
                    // businessProcessName
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getBusinessProcessName(), Types.VARCHAR);
                    // mainBusinessObjectName
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getMainBusinessObjectName(), Types.VARCHAR);
                    // creatorId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getCreatorId(), Types.VARCHAR);
                    // launchCount
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getLaunchCount(), Types.INTEGER);
                    // successCount
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getSuccessCount(), Types.INTEGER);
                    // averageCost
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getAverageCost(), Types.BIGINT);
                    // status
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getStatus(), Types.INTEGER);
                    // lastLaunchTimestamp
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getLastLaunchTimestamp(), Types.TIMESTAMP);
                }
            });
        } catch (Exception e) {
            log.error("[" + businessProcess.getBusinessProcessId() + "]Error on creating business process by businessProcessId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int update(BusinessProcess businessProcess) {
        String sql = "UPDATE boo_business_process SET last_update_timestamp = NOW()";
        // businessProcessName
        if (!StringUtils.isEmpty(businessProcess.getBusinessProcessName())) {
            sql += ", business_process_name = ?";
        }
        // mainBusinessObjectName
        if (!StringUtils.isEmpty(businessProcess.getMainBusinessObjectName())) {
            sql += ", main_business_object_name = ?";
        }
        // creatorId
        if (!StringUtils.isEmpty(businessProcess.getCreatorId())) {
            sql += ", creator_id = ?";
        }
        // launchCount
        if (null != businessProcess.getLaunchCount()) {
            sql += ", launch_count = ?";
        }
        // successCount
        if (null != businessProcess.getSuccessCount()) {
            sql += ", success_count = ?";
        }
        // averageCost
        if (null != businessProcess.getAverageCost()) {
            sql += ", average_cost = ?";
        }
        // status
        if (null != businessProcess.getStatus()) {
            sql += ", status = ?";
        }
        // lastLaunchTimestamp
        if (!StringUtils.isEmpty(businessProcess.getLastLaunchTimestamp())) {
            sql += ", last_launch_timestamp = ?";
        }
        // businessProcessId
        sql += " WHERE business_process_id = ?";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // businessProcessName
                    if (!StringUtils.isEmpty(businessProcess.getBusinessProcessName())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getBusinessProcessName(), Types.VARCHAR);
                    }
                    // mainBusinessObjectName
                    if (!StringUtils.isEmpty(businessProcess.getMainBusinessObjectName())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getMainBusinessObjectName(), Types.VARCHAR);
                    }
                    // creatorId
                    if (!StringUtils.isEmpty(businessProcess.getCreatorId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getCreatorId(), Types.VARCHAR);
                    }
                    // launchCount
                    if (null != businessProcess.getLaunchCount()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getLaunchCount(), Types.INTEGER);
                    }
                    // successCount
                    if (null != businessProcess.getSuccessCount()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getSuccessCount(), Types.INTEGER);
                    }
                    // averageCost
                    if (null != businessProcess.getAverageCost()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getAverageCost(), Types.BIGINT);
                    }
                    // status
                    if (null != businessProcess.getStatus()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getStatus(), Types.INTEGER);
                    }
                    // lastLaunchTimestamp
                    if (!StringUtils.isEmpty(businessProcess.getLastLaunchTimestamp())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getLastLaunchTimestamp(), Types.TIMESTAMP);
                    }
                    JdbcUtil.preparedStatementSetter(ps, index(), businessProcess.getBusinessProcessId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + businessProcess.getBusinessProcessId() + "]Error on updating business process by businessProcessId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public BusinessProcess findOne(String businessProcessId) {
        String sql = "SELECT business_process_id, business_process_name, main_business_object_name, creator_id, " +
                "launch_count, success_count, average_cost, status, last_launch_timestamp " +
                "FROM boo_business_process " +
                "WHERE business_process_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{businessProcessId}, new RowMapper<BusinessProcess>() {
                @Override
                public BusinessProcess mapRow(ResultSet resultSet, int i) throws SQLException {
                    BusinessProcess businessProcess = new BusinessProcess();
                    businessProcess.setBusinessProcessId(resultSet.getString("business_process_id"));
                    businessProcess.setBusinessProcessName(resultSet.getString("business_process_name"));
                    businessProcess.setMainBusinessObjectName(resultSet.getString("main_business_object_name"));
                    businessProcess.setCreatorId(resultSet.getString("creator_id"));
                    businessProcess.setLaunchCount(resultSet.getInt("launch_count"));
                    businessProcess.setSuccessCount(resultSet.getInt("success_count"));
                    businessProcess.setAverageCost(resultSet.getLong("average_cost"));
                    businessProcess.setStatus(resultSet.getInt("status"));
                    businessProcess.setLastLaunchTimestamp(resultSet.getTimestamp("last_launch_timestamp"));
                    return businessProcess;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + businessProcessId + "]Error on querying business process by businessProcessId.", e);
            throw new DAOException(e);
        }
    }
}
