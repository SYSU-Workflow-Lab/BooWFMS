package cn.edu.sysu.workflow.access.dao.impl;

import cn.edu.sysu.workflow.access.dao.AuthorityDAO;
import cn.edu.sysu.workflow.common.entity.access.Authority;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
    public Authority findByAccountIdAndBusinessProcessEntityId(String accountId, String businessProcessEntityId) {
        String sql = "SELECT authority_id, type, account_id, business_process_entity_id " +
                "FROM boo_authority WHERE account_id = ? and business_process_entity_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{accountId, businessProcessEntityId}, (RowMapper<Authority>) (resultSet, i) -> {
                Authority authority = new Authority();
                authority.setAuthorityId(resultSet.getString("authority_id"));
                authority.setType(resultSet.getString("type"));
                authority.setAccountId(resultSet.getString("account_id"));
                authority.setBusinessProcessEntityId(resultSet.getString("business_process_entity_id"));
                return authority;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + accountId + "]Error on querying account by accountId.", e);
            throw new DAOException(e);
        }
    }
}
