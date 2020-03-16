package com.community.controller;

import com.community.dto.AccessTokenDTO;
import com.community.provider.GitHubProvider;
import com.community.provider.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private GitHubProvider provider;

    @RequestMapping("/")
    public String toIndex(){
        return "index";
    }
    @GetMapping("totest")
    public String test(){
        return "test";
    }

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                            Model model
    ){
        System.out.println("进入controller");
        System.out.println(code);
        System.out.println(state);
        AccessTokenDTO accessTokenDTO =new AccessTokenDTO();
        accessTokenDTO.setClient_id("6587a2861b99885fc302");
        accessTokenDTO.setClient_secret("e3099abf62ee158a414bf760d1bddd8c0055bdee");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/cmu/callback");
        String string=provider.getAccessToken(accessTokenDTO);
        GitHubUser user=provider.getUser(string);
        model.addAttribute("githubuser",user);
        return "index";
    }
}
