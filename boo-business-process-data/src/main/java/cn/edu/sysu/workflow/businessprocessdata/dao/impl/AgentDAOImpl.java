package cn.edu.sysu.workflow.businessprocessdata.dao.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.AccountDAO;
import cn.edu.sysu.workflow.businessprocessdata.dao.AgentDAO;
import cn.edu.sysu.workflow.common.entity.access.Agent;
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
 * {@link AgentDAO}
 *
 * @author Skye
 * Created on 2020/4/27
 */
@Repository
public class AgentDAOImpl implements AgentDAO {

    private static final Logger log = LoggerFactory.getLogger(AccountDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Agent agent) {
        String sql = "INSERT INTO boo_agent (agent_id, display_name, location, reentrant_type, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // agentId
                    JdbcUtil.preparedStatementSetter(ps, index(), agent.getAgentId(), Types.VARCHAR);
                    // displayName
                    JdbcUtil.preparedStatementSetter(ps, index(), agent.getDisplayName(), Types.BLOB);
                    // location
                    JdbcUtil.preparedStatementSetter(ps, index(), agent.getLocation(), Types.BLOB);
                    // reentrantType
                    JdbcUtil.preparedStatementSetter(ps, index(), agent.getReentrantType(), Types.INTEGER);
                }
            });
        } catch (Exception e) {
            log.error("[" + agent.getAgentId() + "]Error on creating agent.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public Agent findOne(String agentId) {
        String sql = "SELECT agent_id, display_name, location, reentrant_type " +
                "FROM boo_agent WHERE agent_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{agentId}, new RowMapper<Agent>() {
                @Override
                public Agent mapRow(ResultSet resultSet, int i) throws SQLException {
                    Agent agent = new Agent();
                    agent.setAgentId(resultSet.getString("agent_id"));
                    agent.setDisplayName(resultSet.getString("display_name"));
                    agent.setLocation(resultSet.getString("location"));
                    agent.setReentrantType(resultSet.getInt("reentrant_type"));
                    return agent;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + agentId + "]Error on querying agent by agentId.", e);
            throw new DAOException(e);
        }
    }
}
