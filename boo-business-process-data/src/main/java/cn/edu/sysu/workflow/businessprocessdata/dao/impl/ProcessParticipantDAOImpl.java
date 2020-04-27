package cn.edu.sysu.workflow.businessprocessdata.dao.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.ProcessParticipantDAO;
import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * {@link ProcessParticipantDAO}
 *
 * @author Skye
 * Created on 2020/1/18
 */
@Repository
public class ProcessParticipantDAOImpl implements ProcessParticipantDAO {

    private static final Logger log = LoggerFactory.getLogger(ProcessParticipantDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(ProcessParticipant processParticipant) {
        String sql = "INSERT INTO boo_process_participant " +
                "(process_participant_id, account_id, display_name, type, agent_location, reentrant_type, note, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // processParticipantId
                    JdbcUtil.preparedStatementSetter(ps, index(), processParticipant.getProcessParticipantId(), Types.VARCHAR);
                    // accountId
                    JdbcUtil.preparedStatementSetter(ps, index(), processParticipant.getAccountId(), Types.VARCHAR);
                    // displayName
                    JdbcUtil.preparedStatementSetter(ps, index(), processParticipant.getDisplayName(), Types.VARCHAR);
                    // type
                    JdbcUtil.preparedStatementSetter(ps, index(), processParticipant.getType(), Types.INTEGER);
                    // agentLocation
                    JdbcUtil.preparedStatementSetter(ps, index(), processParticipant.getAgentLocation(), Types.VARCHAR);
                    // reentrantType
                    JdbcUtil.preparedStatementSetter(ps, index(), processParticipant.getReentrantType(), Types.INTEGER);
                    // note
                    JdbcUtil.preparedStatementSetter(ps, index(), processParticipant.getNote(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + processParticipant.getProcessParticipantId() + "]Error on creating process participant by processParticipant.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public ProcessParticipant findByAccountId(String accountId) {
        String sql = "SELECT process_participant_id, account_id, display_name, type, agent_location, reentrant_type, note " +
                "FROM boo_process_participant WHERE account_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{accountId}, new RowMapper<ProcessParticipant>() {
                @Override
                public ProcessParticipant mapRow(ResultSet resultSet, int i) throws SQLException {
                    ProcessParticipant processParticipant = new ProcessParticipant();
                    processParticipant.setProcessParticipantId(resultSet.getString("process_participant_id"));
                    processParticipant.setAccountId(resultSet.getString("account_id"));
                    processParticipant.setDisplayName(resultSet.getString("display_name"));
                    processParticipant.setType(resultSet.getInt("type"));
                    processParticipant.setAgentLocation(resultSet.getString("agent_location"));
                    processParticipant.setReentrantType(resultSet.getInt("reentrant_type"));
                    processParticipant.setNote(resultSet.getString("note"));
                    return processParticipant;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + accountId + "]Error on querying process participant by accountId.", e);
            throw new DAOException(e);
        }
    }
}
