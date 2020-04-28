package cn.edu.sysu.workflow.access.controller;

import cn.edu.sysu.workflow.access.service.AccountService;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Skye
 * Created on 2020/4/28
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

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
    @PostMapping(value = "/account/register")
    public BooReturnForm addAccount(@RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam(value = "level") String level,
                                    @RequestParam(value = "organization") String organization) {
        // logic
        accountService.register(username, password, level, organization);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("register account successfully");
        return booReturnForm;
    }

    /**
     * Request for an auth token by an authorization username and password.
     *
     * @param username user unique name
     * @param password password
     * @return response package
     */
    @PostMapping(value = "/account/login")
    public BooReturnForm Connect(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password) {
        BooReturnForm booReturnForm = new BooReturnForm();

        return booReturnForm;
    }




}
