package cn.edu.sysu.workflow.resource.dao.impl;

import cn.edu.sysu.workflow.common.entity.WorkItemList;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.resource.dao.WorkItemListDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * {@link WorkItemListDAO}
 *
 * @author Skye
 * Created on 2020/3/17
 */
@Repository
public class WorkItemListDAOImpl implements WorkItemListDAO {

    private static final Logger log = LoggerFactory.getLogger(WorkItemListDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(WorkItemList workItemList) {
        String sql = "INSERT INTO boo_work_item_list " +
                "(work_item_list_id, owner_account_id, type, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // workItemListId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItemList.getWorkItemListId(), Types.VARCHAR);
                    // ownerAccountId
                    JdbcUtil.preparedStatementSetter(ps, index(), workItemList.getOwnerAccountId(), Types.VARCHAR);
                    // type
                    JdbcUtil.preparedStatementSetter(ps, index(), workItemList.getType(), Types.INTEGER);
                }
            });
        } catch (Exception e) {
            log.error("[" + workItemList.getWorkItemListId() + "]Error on creating work item list by workItemList.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public WorkItemList findByOwnerAccountIdAndType(String ownerAccountId, Integer type) {
        String sql = "SELECT work_item_list_id, owner_account_id, type " +
                "FROM boo_work_item_list " +
                "WHERE owner_account_id = ? AND type = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{ownerAccountId, type}, (resultSet, i) -> {
                WorkItemList workItemList = new WorkItemList();
                workItemList.setWorkItemListId(resultSet.getString("work_item_list_id"));
                workItemList.setOwnerAccountId(resultSet.getString("owner_account_id"));
                workItemList.setType(resultSet.getInt("type"));
                return workItemList;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + ownerAccountId + "::" + type + "]Error on querying work item list by ownerAccountId and type.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public String findOwnerAccountIdByWorkItemListId(String workItemListId) {
        String sql = "SELECT owner_account_id " +
                "FROM boo_work_item_list " +
                "WHERE work_item_list_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{workItemListId},
                    (resultSet, i) -> resultSet.getString("owner_account_id"));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + workItemListId + "]Error on querying ownerAccountId by workItemListId.", e);
            throw new DAOException(e);
        }
    }
}
