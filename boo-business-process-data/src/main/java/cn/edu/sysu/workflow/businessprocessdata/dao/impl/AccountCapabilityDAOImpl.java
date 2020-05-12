package cn.edu.sysu.workflow.businessprocessdata.dao.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.AccountCapabilityDAO;
import cn.edu.sysu.workflow.common.entity.exception.DAOException;
import cn.edu.sysu.workflow.common.entity.human.AccountCapability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Skye
 * Created on 2020/5/12
 */
@Repository
public class AccountCapabilityDAOImpl implements AccountCapabilityDAO {

    private static final Logger log = LoggerFactory.getLogger(AccountCapabilityDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AccountCapability> findAccountCapabilityByCapabilityId(String capabilityId) {
        String sql = "SELECT account_capability_id, account_id, capability_id " +
                "FROM boo_account_capability " +
                "WHERE capability_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{capabilityId}, (resultSet, i) -> {
                AccountCapability accountCapability = new AccountCapability();
                accountCapability.setAccountCapabilityId(resultSet.getString("account_capability_id"));
                accountCapability.setAccountId(resultSet.getString("account_id"));
                accountCapability.setCapabilityId(resultSet.getString("capability_id"));
                return accountCapability;
            });
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            log.error("[" + capabilityId + "]Error on querying account-capability list by capabilityId.", e);
            throw new DAOException(e);
        }
    }
}
