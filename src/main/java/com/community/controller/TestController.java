package com.community.controller;

import com.community.pojo.User;
import com.community.service.UserService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    private String test(){
        return "yufengtest!";
    }

    @GetMapping("/token")
    private User testFind(){
        String Token="3bf6a71d-ce82-430e-89cc-063e3be94eba";
        return userService.findUserById(Token);
    }
}
