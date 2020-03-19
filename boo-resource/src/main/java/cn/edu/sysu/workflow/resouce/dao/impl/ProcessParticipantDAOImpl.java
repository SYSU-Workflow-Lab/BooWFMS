package cn.edu.sysu.workflow.resouce.dao.impl;

import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.resouce.dao.ProcessParticipantDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

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
