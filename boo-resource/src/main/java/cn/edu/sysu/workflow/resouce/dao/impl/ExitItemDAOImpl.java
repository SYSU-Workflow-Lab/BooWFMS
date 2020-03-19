package cn.edu.sysu.workflow.resouce.dao.impl;

import cn.edu.sysu.workflow.common.entity.ExitItem;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.resouce.dao.ExitItemDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * {@link ExitItemDAO}
 *
 * @author Skye
 * Created on 2020/3/19
 */
@Repository
public class ExitItemDAOImpl implements ExitItemDAO {

    private static final Logger log = LoggerFactory.getLogger(ExitItemDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(ExitItem exitItem) {
        String sql = "INSERT INTO boo_exit_item " +
                "(exit_item_id, work_item_id, process_instance_id, status, visibility, " +
                "reason, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // exitItemId
                    JdbcUtil.preparedStatementSetter(ps, index(), exitItem.getExitItemId(), Types.VARCHAR);
                    // workItemId
                    JdbcUtil.preparedStatementSetter(ps, index(), exitItem.getWorkItemId(), Types.VARCHAR);
                    // processInstanceId
                    JdbcUtil.preparedStatementSetter(ps, index(), exitItem.getProcessInstanceId(), Types.VARCHAR);
                    // status
                    JdbcUtil.preparedStatementSetter(ps, index(), exitItem.getStatus(), Types.INTEGER);
                    // visibility
                    JdbcUtil.preparedStatementSetter(ps, index(), exitItem.getVisibility(), Types.INTEGER);
                    // reason
                    JdbcUtil.preparedStatementSetter(ps, index(), exitItem.getReason(), Types.DOUBLE);
                }
            });
        } catch (Exception e) {
            log.error("[" + exitItem.getExitItemId() + "]Error on creating exit item by exitItem.", e);
            throw new DAOException(e);
        }
    }

}
