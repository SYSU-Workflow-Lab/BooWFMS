package cn.edu.sysu.workflow.access.controller;

import cn.edu.sysu.workflow.access.service.AccountService;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户管理
 *
 * @author Skye
 * Created on 2020/4/30
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Add a new authorization user.
     *
     * @param username     user unique name
     * @param password     user password
     * @param level        user level
     * @param organization organization name
     * @return response package
     */
    @PostMapping(value = "/register")
    public BooReturnForm addAccount(@RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam(value = "level") String level,
                                    @RequestParam(value = "organization") String organization) {
        // logic
        accountService.register(username, password, level, organization);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("Register account successfully");
        return booReturnForm;
    }
}
