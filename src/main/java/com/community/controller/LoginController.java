package com.community.controller;

import com.community.dto.AccessTokenDTO;
import com.community.provider.GitHubProvider;
import com.community.provider.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Value("${github.client.id}")
    private String clientid;
    @Value("${github.client.secret}")
    private String clientsecret;
    @Value("${github.client.redirect_url}")
    private String redirecturl;

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

    /*验证git登录*/
    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                            HttpSession session
    ){
        System.out.println("进入controller");
        System.out.println(code);
        System.out.println(state);
        AccessTokenDTO accessTokenDTO =new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientid);
        accessTokenDTO.setClient_secret(clientsecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirecturl);
        String string=provider.getAccessToken(accessTokenDTO);
        GitHubUser user=provider.getUser(string);
        session.setAttribute("user",user);

        return "redirect:/";
    }
}
