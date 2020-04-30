package cn.edu.sysu.workflow.access.controller;

import cn.edu.sysu.workflow.access.service.AccountService;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
        booReturnForm.setMessage("Register account successfully");
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
    public BooReturnForm login(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password,
                               HttpSession httpSession) {
        String accountId = accountService.login(username, password);
        BooReturnForm booReturnForm = new BooReturnForm();
        if (!StringUtils.isEmpty(accountId)) {
            booReturnForm.setMessage("Login successfully!");
            httpSession.setAttribute("accountId", accountId);
        } else {
            booReturnForm.setMessage("Login in failed");
        }
        return booReturnForm;
    }

    @RequestMapping(value = "/account/logout")
    public BooReturnForm logout(HttpSession httpSession) {
        httpSession.removeAttribute("accountId");

        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("Logout successfully!");
        return booReturnForm;
    }

    @GetMapping(value = "/account/echo")
    public BooReturnForm echo(HttpSession httpSession) {
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage((String) httpSession.getAttribute("accountId"));
        return booReturnForm;
    }

}
