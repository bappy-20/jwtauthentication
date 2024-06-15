package com.jwt.jwtauthentication.controller;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
@RequestMapping("/welcome")
    public String welcome(){
        String text = "thi is private page";
        text += "this page is not for unathorized User";
        return text;
    }

    @RequestMapping("/getusers")
    public String getUsers(){
     return "{\"name\":\"bappy\"}";
    }
}
