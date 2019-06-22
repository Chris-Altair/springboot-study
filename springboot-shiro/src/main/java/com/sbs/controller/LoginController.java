package com.sbs.controller;

import com.sbs.common.R;
import com.sbs.domain.UserDO;
import com.sbs.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private RedisSessionDAO sessionDAO;

    @GetMapping({ "/", "" })
    public String welcome(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        UserDO userDO = ShiroUtils.getUser();
        if (userDO != null) {
            return "redirect:/index";
        }
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public R ajaxLogin(String username, String password, boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
            return R.error(e.getMessage());
        } catch (Exception e) {
            return R.error("请联系管理员！");
        }
    }

    @GetMapping("/index")
    public String index(Model model){
        UserDO user = ShiroUtils.getUser();
        model.addAttribute("size", sessionDAO.getActiveSessions().size());
        if(null == user){
            return "redirect:/";
        }
        return "index";
    }

}
