package cn.edu.sysu.workflow.engine.dao.impl;

import cn.edu.sysu.workflow.common.entity.BinStep;
import cn.edu.sysu.workflow.common.entity.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.engine.dao.BinStepDAO;
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
 * @see cn.edu.sysu.workflow.engine.dao.BinStepDAO
 * @author Skye
 * Created on 2019/12/31
 */
@Repository
public class BinStepDAOImpl implements BinStepDAO {

    private static final Logger log = LoggerFactory.getLogger(BinStepDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(BinStep binStep) {
        String sql = "INSERT INTO boo_bin_step " +
                "(bin_step_id, process_instance_id, parent_id, notification_id, binlog, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // binStepId
                    JdbcUtil.preparedStatementSetter(ps, index(), binStep.getBinStepId(), Types.VARCHAR);
                    // processInstanceId
                    JdbcUtil.preparedStatementSetter(ps, index(), binStep.getProcessInstanceId(), Types.VARCHAR);
                    // parentId
                    JdbcUtil.preparedStatementSetter(ps, index(), binStep.getParentId(), Types.VARCHAR);
                    // notificationId
                    JdbcUtil.preparedStatementSetter(ps, index(), binStep.getNotificationId(), Types.VARCHAR);
                    // binlog
                    JdbcUtil.preparedStatementSetter(ps, index(), binStep.getBinlog(), Types.BLOB);
                }
            });
        } catch (Exception e) {
            log.error("[" + binStep.getBinStepId() + "]Error on creating bin step by binStepId.", e);
            return 0;
        }
    }

    @Override
    public int update(BinStep binStep) {
        String sql = "UPDATE boo_bin_step SET last_update_timestamp = NOW()";
        // processInstanceId
        if (!StringUtils.isEmpty(binStep.getProcessInstanceId())) {
            sql += ", process_instance_id = ?";
        }
        // parentId
        if (!StringUtils.isEmpty(binStep.getParentId())) {
            sql += ", parent_id = ?";
        }
        // notificationId
        if (!StringUtils.isEmpty(binStep.getNotificationId())) {
            sql += ", notification_id = ?";
        }
        // binlog
        if (null != binStep.getBinlog() && 0 != binStep.getBinlog().length) {
            sql += ", binlog = ?";
        }
        // binStepId
        sql += " WHERE bin_step_id = ?";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // processInstanceId
                    if (!StringUtils.isEmpty(binStep.getProcessInstanceId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), binStep.getProcessInstanceId(), Types.VARCHAR);
                    }
                    // parentId
                    if (!StringUtils.isEmpty(binStep.getParentId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), binStep.getParentId(), Types.VARCHAR);
                    }
                    // notificationId
                    if (!StringUtils.isEmpty(binStep.getNotificationId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), binStep.getNotificationId(), Types.VARCHAR);
                    }
                    // binlog
                    if (null != binStep.getBinlog() && 0 != binStep.getBinlog().length) {
                        JdbcUtil.preparedStatementSetter(ps, index(), binStep.getBinlog(), Types.BLOB);
                    }
                    // binStepId
                    JdbcUtil.preparedStatementSetter(ps, index(), binStep.getBinStepId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + binStep.getBinStepId() + "]Error on updating bin step by binStepId.", e);
            return 0;
        }
    }

    @Override
    public BinStep findOne(String binStepId) {
        String sql = "SELECT bin_step_id, process_instance_id, parent_id, notification_id, binlog " +
                "FROM boo_bin_step " +
                "WHERE bin_step_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{binStepId}, new RowMapper<BinStep>() {
                @Override
                public BinStep mapRow(ResultSet resultSet, int i) throws SQLException {
                    BinStep binStep = new BinStep();
                    binStep.setBinStepId(resultSet.getString("bin_step_id"));
                    binStep.setProcessInstanceId(resultSet.getString("process_instance_id"));
                    binStep.setParentId(resultSet.getString("parent_id"));
                    binStep.setNotificationId(resultSet.getString("notification_id"));
                    binStep.setBinlog(resultSet.getBytes("binlog"));
                    return binStep;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + binStepId + "]Error on querying process instance by processInstanceId.", e);
            return null;
        }
    }

    @Override
    public List<BinStep> findBinStepsByProcessInstanceId(String processInstanceId) {
        String sql = "SELECT bin_step_id, process_instance_id, parent_id, notification_id, binlog " +
                "FROM boo_bin_step " +
                "WHERE process_instance_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{processInstanceId}, new RowMapper<BinStep>() {
                @Override
                public BinStep mapRow(ResultSet resultSet, int i) throws SQLException {
                    BinStep binStep = new BinStep();
                    binStep.setBinStepId(resultSet.getString("bin_step_id"));
                    binStep.setProcessInstanceId(resultSet.getString("process_instance_id"));
                    binStep.setParentId(resultSet.getString("parent_id"));
                    binStep.setNotificationId(resultSet.getString("notification_id"));
                    binStep.setBinlog(resultSet.getBytes("binlog"));
                    return binStep;
                }
            });
        } catch (Exception e) {
            log.error("[" + processInstanceId + "]Error on querying bin step list by processInstanceId.", e);
            return null;
        }
    }

    @Override
    public int deleteByProcessInstanceId(String processInstanceId) {
        String sql = "DELETE FROM boo_bin_step WHERE process_instance_id = ?";
        try {
            return jdbcTemplate.update(sql, processInstanceId);
        } catch (Exception e) {
            log.error("[" + processInstanceId + "]Error on deleting bin step list by processInstanceId.", e);
            return 0;
        }
    }

}
