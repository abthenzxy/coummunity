package com.community.controller;

import com.community.dto.AccessTokenDTO;
import com.community.pojo.User;
import com.community.provider.GitHubProvider;
import com.community.provider.GitHubUser;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Value("${github.client.id}")
    private String clientid;
    @Value("${github.client.secret}")
    private String clientsecret;
    @Value("${github.client.redirect_url}")
    private String redirecturl;

    @Autowired
    private GitHubProvider provider;

    @RequestMapping("/")
    public String toIndex(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token=null;
        /*需要做非空判断否则会报异常*/
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    token=cookie.getValue();
                    break;
                }
            }
        }
        User user=userService.findUserById(token);
        if(user!=null){
            System.out.println(user);
            request.getSession().setAttribute("user",user);
        }
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
                            HttpServletResponse response
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
        GitHubUser gitHubUser=provider.getUser(string);
        if(gitHubUser!=null){
            User user = new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userService.addUser(user);
            System.out.println(user.getId());
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            String msg="登录失败";
            System.out.println(msg);
            response.addCookie(new Cookie("msg",msg));
            return "redirect:/";
        }

    }
}
