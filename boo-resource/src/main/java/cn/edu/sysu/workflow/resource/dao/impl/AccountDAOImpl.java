package cn.edu.sysu.workflow.resource.dao.impl;

import cn.edu.sysu.workflow.common.entity.access.Account;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.resource.dao.AccountDAO;
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
 * {@link AccountDAO}
 *
 * @author Skye
 * Created on 2020/4/27
 */
@Repository
public class AccountDAOImpl implements AccountDAO {

    private static final Logger log = LoggerFactory.getLogger(AccountDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account findSimpleOne(String accountId) {
        String sql = "SELECT account_id, username, organization_name, status, level " +
                "FROM boo_account WHERE account_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{accountId}, new RowMapper<Account>() {
                @Override
                public Account mapRow(ResultSet resultSet, int i) throws SQLException {
                    Account account = new Account();
                    account.setAccountId(resultSet.getString("account_id"));
                    account.setUsername(resultSet.getString("username"));
                    account.setOrganizationName(resultSet.getString("organization_name"));
                    account.setStatus(resultSet.getInt("status"));
                    account.setLevel(resultSet.getInt("level"));
                    return account;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + accountId + "]Error on querying account by accountId.", e);
            throw new DAOException(e);
        }
    }
}
