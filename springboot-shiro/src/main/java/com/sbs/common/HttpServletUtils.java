package com.sbs.common;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HttpServletUtils {
    public static boolean jsAjax(HttpServletRequest req){
        //判断是否为ajax请求，默认不是
        boolean isAjaxRequest = false;
        if(!StringUtils.isEmpty(req.getHeader("x-requested-with")) && req.getHeader("x-requested-with").equals("XMLHttpRequest")){
            isAjaxRequest = true;
        }
        return isAjaxRequest;
    }
}
