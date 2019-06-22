package com.sbs.controller;

import com.sbs.domain.UserDO;
import com.sbs.service.UserService;
import com.sbs.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/test")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;

    @GetMapping()
//    @RequiresPermissions("sys:test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDO user = ShiroUtils.getUser();
        logger.debug("test" + session.getId());
        return user.getUserId() + ";" + user.getUsername() + ";" + session.getId();
    }

}
