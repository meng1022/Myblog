package com.example.blog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blog.dao.GitUserDao;
import com.example.blog.entity.GitUser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GitUserService {
    @Resource
    GitUserDao gitUserDao;

    public String getToken(String url) throws Exception{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String responseStr = EntityUtils.toString(responseEntity);
        String[] responses = responseStr.split("&");
        int index = responses[0].indexOf("access_token");
        if(index==-1)
            throw new Exception("no access_token");
        else{
            String access_token = responses[0].substring("access_token=".length());
            return access_token;
        }
    }

    public GitUser getUser(String access_token) throws Exception{
        String get_user_url = "https://api.github.com/user";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(get_user_url);
        httpGet.setHeader("Authorization","token "+access_token);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        String responseStr = EntityUtils.toString(responseEntity);
        JSONObject json = JSON.parseObject(responseStr);
        long user_id = Long.valueOf(json.getString("id"));
        String user_name = json.getString("login");
        GitUser user = new GitUser(user_id,user_name);
        return gitUserDao.save(user);
//        return insertUser(user_id,user_name);

    }

}
