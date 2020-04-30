package cn.edu.sysu.workflow.access.dao.impl;

import cn.edu.sysu.workflow.access.dao.AccountDAO;
import cn.edu.sysu.workflow.common.entity.access.Account;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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
    public int save(Account account) {
        String sql = "INSERT INTO boo_account (account_id, username, password, salt, organization_name, status, level, " +
                "create_timestamp, last_update_timestamp) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // accountId
                    JdbcUtil.preparedStatementSetter(ps, index(), account.getAccountId(), Types.VARCHAR);
                    // username
                    JdbcUtil.preparedStatementSetter(ps, index(), account.getUsername(), Types.VARCHAR);
                    // password
                    JdbcUtil.preparedStatementSetter(ps, index(), account.getPassword(), Types.BLOB);
                    // salt
                    JdbcUtil.preparedStatementSetter(ps, index(), account.getSalt(), Types.VARCHAR);
                    // organizationName
                    JdbcUtil.preparedStatementSetter(ps, index(), account.getOrganizationName(), Types.VARCHAR);
                    // status
                    JdbcUtil.preparedStatementSetter(ps, index(), account.getStatus(), Types.INTEGER);
                    // level
                    JdbcUtil.preparedStatementSetter(ps, index(), account.getLevel(), Types.INTEGER);
                }
            });
        } catch (DuplicateKeyException e) {
            log.error("[" + account.getAccountId() + "]Error on creating account because of Duplicate entry.", e);
            return 0;
        } catch (Exception e) {
            log.error("[" + account.getAccountId() + "]Error on creating account.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int update(Account account) {
        String sql = "UPDATE boo_account SET last_update_timestamp = NOW()";
        // password
        if (!StringUtils.isEmpty(account.getPassword())) {
            sql += ", password = ?";
        }
        // salt
        if (!StringUtils.isEmpty(account.getSalt())) {
            sql += ", salt = ?";
        }
        // organizationName
        if (!StringUtils.isEmpty(account.getOrganizationName())) {
            sql += ", organization_name = ?";
        }
        // status
        if (null != account.getStatus()) {
            sql += ", status = ?";
        }
        // level
        if (null != account.getLevel()) {
            sql += ", level = ?";
        }
        // username
        sql += " WHERE username = ?";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // password
                    if (!StringUtils.isEmpty(account.getPassword())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), account.getPassword(), Types.VARCHAR);
                    }
                    // salt
                    if (!StringUtils.isEmpty(account.getSalt())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), account.getSalt(), Types.VARCHAR);
                    }
                    // organizationName
                    if (!StringUtils.isEmpty(account.getOrganizationName())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), account.getOrganizationName(), Types.VARCHAR);
                    }
                    // status
                    if (null != account.getStatus()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), account.getStatus(), Types.INTEGER);
                    }
                    // level
                    if (null != account.getLevel()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), account.getLevel(), Types.INTEGER);
                    }
                    // username
                    JdbcUtil.preparedStatementSetter(ps, index(), account.getUsername(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + account.getAccountId() + "]Error on updating account by accountId.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int deleteByUsername(String username) {
        String sql = "DELETE FROM boo_account WHERE username = ?";
        try {
            return jdbcTemplate.update(sql, username);
        } catch (Exception e) {
            log.error("[" + username + "]Error on deleting account by username.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public int updateLastLoginTimestampByAccountId(String accountId) {
        String sql = "UPDATE boo_account SET last_login_timestamp = NOW(), " +
                " last_update_timestamp = NOW() WHERE account_id = ?";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // accountId
                    JdbcUtil.preparedStatementSetter(ps, index(), accountId, Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + accountId + "]Error on updating last login timestamp by accountId.", e);
            throw new DAOException(e);
        }
    }

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

    @Override
    public String findSaltByUsername(String username) {
        String sql = "SELECT salt " +
                "FROM boo_account WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("salt");
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + username + "]Error on querying salt by username.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public String findAccountIdByUsernameAndPassword(String username, String password) {
        String sql = "SELECT account_id " +
                "FROM boo_account " +
                "WHERE username = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username, password}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("account_id");
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + username + "::" + password + "]Error on checking account by username and password.", e);
            throw new DAOException(e);
        }
    }

    @Override
    public Boolean checkAccountByUsername(String username) {
        String sql = "SELECT EXISTS(SELECT * " +
                "FROM boo_account " +
                "WHERE username = ?)";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, new RowMapper<Boolean>() {
                @Override
                public Boolean mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getBoolean(1);
                }
            });
        } catch (Exception e) {
            log.error("[" + username + "]Error on checking account by username.", e);
            throw new DAOException(e);
        }
    }
}
