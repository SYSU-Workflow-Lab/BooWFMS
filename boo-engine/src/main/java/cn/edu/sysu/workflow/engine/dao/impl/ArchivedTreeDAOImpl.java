package cn.edu.sysu.workflow.engine.dao.impl;

import cn.edu.sysu.workflow.common.entity.ArchivedTree;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.entity.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.engine.dao.ArchivedTreeDAO;
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
 * {@link ArchivedTreeDAO}
 *
 * @author Skye
 * Created on 2019/12/31
 */
@Repository
public class ArchivedTreeDAOImpl implements ArchivedTreeDAO {

    private static final Logger log = LoggerFactory.getLogger(ArchivedTreeDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(ArchivedTree archivedTree) {
        String sql = "INSERT INTO boo_archived_tree (process_instance_id, tree, create_timestamp, last_update_timestamp) VALUE (?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // processInstanceId
                    JdbcUtil.preparedStatementSetter(ps, index(), archivedTree.getProcessInstanceId(), Types.VARCHAR);
                    // tree
                    JdbcUtil.preparedStatementSetter(ps, index(), archivedTree.getTree(), Types.VARCHAR);

                }
            });
        } catch (Exception e) {
            log.error("[" + archivedTree.getProcessInstanceId() + "]Error on creating archived tree by processInstanceId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public ArchivedTree findOne(String processInstanceId) {
        String sql = "SELECT process_instance_id, tree FROM boo_archived_tree WHERE process_instance_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{processInstanceId}, new RowMapper<ArchivedTree>() {
                @Override
                public ArchivedTree mapRow(ResultSet resultSet, int i) throws SQLException {
                    ArchivedTree archivedTree = new ArchivedTree();
                    archivedTree.setProcessInstanceId(resultSet.getString("process_instance_id"));
                    archivedTree.setTree(resultSet.getString("tree"));
                    return archivedTree;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + processInstanceId + "]Error on querying archived tree by processInstanceId.", e);
            throw new DAOException(e);
        }
    }

}
