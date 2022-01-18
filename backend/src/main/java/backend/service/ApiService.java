package backend.service;

import backend.Result;
import backend.entity.GitUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@Transactional
public class ApiService {
    @Autowired
    HibernateTemplate hibernateTemplate;

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
//        GitUser user = new GitUser(user_id,user_name);
        return insertUser(user_id,user_name);
    }

    //insert or update user
    public GitUser insertUser(Long userid,String username){
        GitUser user = hibernateTemplate.get(GitUser.class,userid);
        if(user==null){
            GitUser gitUser = new GitUser(userid,username);
            hibernateTemplate.save(gitUser);
        }
        else{
            if(!user.getUsername().equals(username)){
                user.setUsername(username);
                hibernateTemplate.update(user);
            }
        }
        return user;
    }

}
