package cn.edu.sysu.workflow.access.service.impl;

import cn.edu.sysu.workflow.access.dao.AccountDAO;
import cn.edu.sysu.workflow.access.service.AccountService;
import cn.edu.sysu.workflow.common.entity.access.Account;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import cn.edu.sysu.workflow.common.enums.AccountLevel;
import cn.edu.sysu.workflow.common.util.EncryptUtil;
import cn.edu.sysu.workflow.common.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

/**
 * {@link AccountService}
 *
 * @author Skye
 * Created on 2020/4/28
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDAO accountDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(String username, String password, String level, String organization) {
        // check exist
        if (this.checkAccountByUsernameAndOrganizationName(username,organization)) {
            return false;
        }

        // 检查密码是否为空
        if (StringUtils.isEmpty(password)) {
            return false;
        }

        try {
            Account account = new Account();
            account.setAccountId(Account.PREFIX + IdUtil.nextId());
            account.setUsername(username);
            account.setLevel(AccountLevel.valueOf(level).ordinal());
            account.setOrganizationName(organization);
            account.setStatus(1);
            // 密码加盐做摘要
            String salt = EncryptUtil.getSalt();
            account.setSalt(salt);
            String finalPassword = EncryptUtil.encryptSHA256(EncryptUtil.encryptSHA256(password) + salt);
            account.setPassword(finalPassword);

            accountDAO.save(account);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(String.format("create account but exception occurred (%s@%s), service rollback, %s", username, organization, e));
            throw new ServiceFailureException(String.format("create account but exception occurred (%s@%s), service rollback", username, organization), e);
        }
    }

    @Override
    public boolean checkAccountByUsernameAndOrganizationName(String username, String organizationName) {
        return accountDAO.checkAccountByUsernameAndOrganizationName(username, organizationName);
    }
}
