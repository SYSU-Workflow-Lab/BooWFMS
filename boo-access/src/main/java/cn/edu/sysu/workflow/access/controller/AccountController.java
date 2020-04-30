package cn.edu.sysu.workflow.access.controller;

import cn.edu.sysu.workflow.access.service.AccountService;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
     * Add a new user.
     *
     * @param username     user unique name
     * @param password     user password
     * @param organization organization name
     * @param level        user level
     * @return response package
     */
    @PostMapping(value = "/register")
    public BooReturnForm register(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "password") String password,
                                  @RequestParam(value = "organization") String organization,
                                  @RequestParam(value = "level") String level) {
        // logic
        accountService.register(username, password, organization, level);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("Register account successfully");
        return booReturnForm;
    }

    /**
     * Update a user.
     *
     * @param username     user unique name
     * @param organization organization name
     * @param status       status
     * @param level        user level
     * @return response package
     */
    @PostMapping(value = "/update")
    public BooReturnForm update(@RequestParam(value = "username", required = false) String username,
                                @RequestParam(value = "organization", required = false) String organization,
                                @RequestParam(value = "status", required = false) Integer status,
                                @RequestParam(value = "level", required = false) String level,
                                HttpSession httpSession) {
        // logic
        accountService.update(username, organization, status, level);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("Update account successfully");
        return booReturnForm;
    }

    @PostMapping(value = "/delete")
    public BooReturnForm delete(@RequestParam(value = "username") String username, HttpSession httpSession) {
        // logic
        accountService.delete(username);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("Delete account successfully");
        return booReturnForm;
    }
}
