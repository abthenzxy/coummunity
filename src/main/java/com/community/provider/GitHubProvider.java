package com.community.provider;

import com.alibaba.fastjson.JSON;
import com.community.dto.AccessTokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author young
 */
@Component
public class GitHubProvider {
    /*获取令牌*/
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            String[] split=string.split("&");
            string=split[0].split("=")[1].toString();
            System.out.println("accessToken="+string);
            return string;
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return null;

    }

    /*传递令牌至github获取user信息*/
    public GitHubUser getUser(String accesstoken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accesstoken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            GitHubUser gitHubUser= JSON.parseObject(string,GitHubUser.class);
            return gitHubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
