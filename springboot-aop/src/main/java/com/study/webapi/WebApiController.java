package com.study.webapi;

import com.study.annection.Verification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebApiController {

    @Verification
    @GetMapping("/before")
    public String before(@RequestParam(name = "name") String name,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "flag")Integer flag){
        System.out.println("方法内");
        return name+":"+password;
    }

    @Verification
    @PostMapping("/api")
    public String api(@RequestParam(name = "name") String name,
                      @RequestParam(name = "password") String password,
                      @RequestParam(name = "flag")Integer flag,
                      @RequestParam(name = "timestamp") Long timestamp,
                      @RequestParam(name = "sign") String sign){
        System.out.println("api方法内");
        return "api:"+name+","+password;
    }
}
