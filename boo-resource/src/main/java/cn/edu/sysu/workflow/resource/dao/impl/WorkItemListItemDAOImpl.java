package cn.edu.sysu.workflow.resource.dao.impl;

import cn.edu.sysu.workflow.common.entity.WorkItemListItem;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.resource.dao.WorkItemListItemDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link WorkItemListItemDAO}
 *
 * @author Skye
 * Created on 2020/3/14
 */
@Repository
public class WorkItemListItemDAOImpl implements WorkItemListItemDAO {

    private static final Logger log = LoggerFactory.getLogger(WorkItemListItemDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(WorkItemListItem workItemListItem) {
        String sql = "INSERT INTO boo_work_item_list_item " +
                "(work_item_list_item_id, work_item_list_id, work_item_id, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // workItemListItemId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItemListItem.getWorkItemListItemId(), Types.VARCHAR);
                    // workItemListId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItemListItem.getWorkItemListId(), Types.VARCHAR);
                    // workItemId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItemListItem.getWorkItemId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + workItemListItem.getWorkItemListItemId() + "]Error on creating work item list item by workItemListItem.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int update(WorkItemListItem workItemListItem) {
        String sql = "UPDATE boo_work_item_list_item SET last_update_timestamp = NOW()";
        // workItemListId
        if (!StringUtils.isEmpty(workItemListItem.getWorkItemListId())) {
            sql += ", work_item_list_id = ?";
        }
        // workItemId
        if (!StringUtils.isEmpty(workItemListItem.getWorkItemId())) {
            sql += ", work_item_id = ?";
        }
        // workItemListItemId
        sql += " WHERE work_item_list_item_id = ?";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // workItemListId
                    if (!StringUtils.isEmpty(workItemListItem.getWorkItemListId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItemListItem.getWorkItemListId(), Types.VARCHAR);
                    }
                    // workItemId
                    if (!StringUtils.isEmpty(workItemListItem.getWorkItemId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), workItemListItem.getWorkItemId(), Types.VARCHAR);
                    }
                    // workItemListItemId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItemListItem.getWorkItemListItemId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + workItemListItem.getWorkItemListItemId() + "]Error on updating work item list item by workItemListItemId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int deleteByWorkItemListIdAndWorkItemId(String workItemListId, String workItemId) {
        String sql = "DELETE FROM boo_work_item_list_item WHERE work_item_list_id = ? AND work_item_id = ?";
        try {
            return jdbcTemplate.update(sql, workItemListId, workItemId);
        } catch (Exception e) {
            log.error("[" + workItemId + "]Error on deleting work item list item by workItemListId and workItemId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int deleteByWorkItemId(String workItemId) {
        String sql = "DELETE FROM boo_work_item_list_item WHERE work_item_id = ?";
        try {
            return jdbcTemplate.update(sql, workItemId);
        } catch (Exception e) {
            log.error("[" + workItemId + "]Error on deleting work item list item by workItemId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<WorkItemListItem> findWorkItemListItemsByWorkItemListId(String workItemListId) {
        String sql = "SELECT work_item_list_item_id, work_item_list_id, work_item_id " +
                "FROM boo_work_item_list_item " +
                "WHERE work_item_list_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{workItemListId}, (RowMapper<WorkItemListItem>) (resultSet, i) -> {
                WorkItemListItem workItemListItem = new WorkItemListItem();
                workItemListItem.setWorkItemListItemId(resultSet.getString("work_item_list_item_id"));
                workItemListItem.setWorkItemListId(resultSet.getString("work_item_list_id"));
                workItemListItem.setWorkItemId(resultSet.getString("work_item_id"));
                return workItemListItem;
            });
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            log.error("[" + workItemListId + "]Error on querying work item list item list by workItemListId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<WorkItemListItem> findWorkItemListItemsByWorkItemId(String workItemId) {
        String sql = "SELECT work_item_list_item_id, work_item_list_id, work_item_id " +
                "FROM boo_work_item_list_item " +
                "WHERE work_item_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{workItemId}, (RowMapper<WorkItemListItem>) (resultSet, i) -> {
                WorkItemListItem workItemListItem = new WorkItemListItem();
                workItemListItem.setWorkItemListItemId(resultSet.getString("work_item_list_item_id"));
                workItemListItem.setWorkItemListId(resultSet.getString("work_item_list_id"));
                workItemListItem.setWorkItemId(resultSet.getString("work_item_id"));
                return workItemListItem;
            });
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            log.error("[" + workItemId + "]Error on querying work item list item list by workItemId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public WorkItemListItem findByWorkItemListIdAndWorkItemId(String workItemListId, String workItemId) {
        String sql = "SELECT work_item_list_item_id, work_item_list_id, work_item_id " +
                "FROM boo_work_item_list_item " +
                "WHERE work_item_list_id = ? AND work_item_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{workItemListId, workItemId}, (resultSet, i) -> {
                WorkItemListItem workItemListItem = new WorkItemListItem();
                workItemListItem.setWorkItemListItemId(resultSet.getString("work_item_list_item_id"));
                workItemListItem.setWorkItemListId(resultSet.getString("work_item_list_id"));
                workItemListItem.setWorkItemId(resultSet.getString("work_item_id"));
                return workItemListItem;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + workItemListId + "::" + workItemId + "]Error on querying work item list item by workItemListId and workItemId.", e);
            throw new DAOException(e);
        }
    }
}
