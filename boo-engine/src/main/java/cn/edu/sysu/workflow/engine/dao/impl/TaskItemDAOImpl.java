package cn.edu.sysu.workflow.engine.dao.impl;

import cn.edu.sysu.workflow.common.entity.TaskItem;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.entity.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.engine.dao.TaskItemDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * {@link TaskItemDAO}
 *
 * @author Skye
 * Created on 2020/1/2
 */
@Repository
public class TaskItemDAOImpl implements TaskItemDAO {

    private static final Logger log = LoggerFactory.getLogger(TaskItemDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(TaskItem taskItem) {
        String sql = "INSERT INTO boo_task_item " +
                "(task_item_id, business_object_id, task_polymorphism_id, task_polymorphism_name, business_role, " +
                "principle, event_descriptor, hook_descriptor, documentation, parameters, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // taskItemId
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getTaskItemId(), Types.VARCHAR);
                    // businessObjectId
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getBusinessObjectId(), Types.VARCHAR);
                    // taskPolymorphismId
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getTaskPolymorphismId(), Types.VARCHAR);
                    // taskPolymorphismName
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getTaskPolymorphismName(), Types.VARCHAR);
                    // businessRole
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getBusinessRole(), Types.BLOB);
                    // principle
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getPrinciple(), Types.VARCHAR);
                    // eventDescriptor
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getEventDescriptor(), Types.VARCHAR);
                    // hookDescriptor
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getHookDescriptor(), Types.VARCHAR);
                    // documentation
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getDocumentation(), Types.VARCHAR);
                    // parameters
                    JdbcUtil.preparedStatementSetter(ps, index(), taskItem.getParameters(), Types.BLOB);
                }
            });
        } catch (Exception e) {
            log.error("[" + taskItem.getTaskItemId() + "]Error on creating task item by taskItemId.", e);
            throw new DAOException(e);
        }
    }
}
