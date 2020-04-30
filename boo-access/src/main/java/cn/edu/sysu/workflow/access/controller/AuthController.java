package cn.edu.sysu.workflow.access.controller;

import cn.edu.sysu.workflow.access.service.AccountService;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 登录登出权限控制
 *
 * @author Skye
 * Created on 2020/4/28
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AccountService accountService;

    /**
     * Login and set accountId to session.
     *
     * @param username user unique name
     * @param password password
     * @return response package
     */
    @PostMapping(value = "/login")
    public BooReturnForm login(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password,
                               HttpSession httpSession) {
        // logic
        String accountId = accountService.login(username, password);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        if (!StringUtils.isEmpty(accountId)) {
            booReturnForm.setMessage("Login successfully!");
            httpSession.setAttribute("accountId", accountId);
        } else {
            booReturnForm.setMessage("Login in failed");
        }
        return booReturnForm;
    }

    /**
     * Logout and remove accountId from session.
     *
     * @return response package
     */
    @RequestMapping(value = "/logout")
    public BooReturnForm logout(HttpSession httpSession) {
        // logic
        httpSession.removeAttribute("accountId");

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("Logout successfully!");
        return booReturnForm;
    }

    /**
     * Echo the accountId of session
     *
     * @return response package
     */
    @GetMapping(value = "/echo")
    public BooReturnForm echo(HttpSession httpSession) {
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage((String) httpSession.getAttribute("accountId"));
        return booReturnForm;
    }

}
