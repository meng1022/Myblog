package backend.service;

import backend.Result;
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
import org.springframework.stereotype.Component;

@Component
public class ApiService {

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

    public String getUser(String access_token) throws Exception{
        String get_user_url = "https://api.github.com/user";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(get_user_url);
        httpGet.setHeader("Authorization","token "+access_token);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        String responseStr = EntityUtils.toString(responseEntity);
        JSONObject json = JSON.parseObject(responseStr);
        String user_id = json.getString("id");
        String user_name = json.getString("login");
//        System.out.println(responseStr);
        return user_id;
    }


}
