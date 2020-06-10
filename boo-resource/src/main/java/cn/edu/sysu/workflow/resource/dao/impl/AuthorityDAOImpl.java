package cn.edu.sysu.workflow.resource.dao.impl;

import cn.edu.sysu.workflow.common.entity.access.Authority;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.resource.dao.AuthorityDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * {@link AuthorityDAO}
 *
 * @author Skye
 * Created on 2020/6/10
 */
@Repository
public class AuthorityDAOImpl implements AuthorityDAO {

    private static final Logger log = LoggerFactory.getLogger(AuthorityDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Authority authority) {
        String sql = "INSERT INTO boo_authority " +
                "(authority_id, type, account_id, business_process_entity_id, create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // authorityId
                    JdbcUtil.preparedStatementSetter(ps, index(), authority.getAuthorityId(), Types.VARCHAR);
                    // type
                    JdbcUtil.preparedStatementSetter(ps, index(), authority.getType(), Types.VARCHAR);
                    // accountId
                    JdbcUtil.preparedStatementSetter(ps, index(), authority.getAccountId(), Types.VARCHAR);
                    // businessProcessEntityId
                    JdbcUtil.preparedStatementSetter(ps, index(), authority.getBusinessProcessEntityId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + authority.getAccountId() + "::" + authority.getBusinessProcessEntityId()
                    + "]Error on creating authority by authority.", e);
            throw new DAOException(e);
        }
    }
}
