package backend.controller;

import backend.Result;
import backend.entity.GitUser;
import backend.entity.User;
import backend.service.ApiService;
import backend.service.UserService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

//Replace @Controller with @RestController
// do not need to write consumes... produces...
// json data type
@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    UserService userService;
    @Autowired
    ApiService apiService;

//    @GetMapping("/user/{id}")
//    public User getuser(@PathVariable("id")long id){
//        User user = userService.getUserById(id);
//        return user;
//    }

//    @GetMapping("/getToken")
//    public Result getToken(@RequestParam("code")String code){
//        String url = "https://github.com/login/oauth/access_token?client_id=a5eca1aecf53810e6a8e&"
//                    +"client_secret=5f57b71ca6b1282d263221ae48a2cd72601896a7&"
//                    +"code="+code;
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost(url);
//        CloseableHttpResponse response = null;
//        try{
//            response = httpClient.execute(httpPost);
//            HttpEntity responseEntity = response.getEntity();
//            String responseStr = EntityUtils.toString(responseEntity);
//            String[] responses = responseStr.split("&");
//            int index = responses[0].indexOf("access_token");
//            if(index==-1)
//                throw new Exception("no access_token");
//            else{
//                String access_token = responses[0].substring("access_token=".length());
//                return Result.SetOk(access_token);
//            }
//
//        }catch (Exception e){
//            return Result.SetError(e.getMessage());
//        }
//    }

    @GetMapping("/getUserinfo")
    public Result getUserinfo(@RequestParam("code")String code){
        String get_token_url = "https://github.com/login/oauth/access_token?client_id=a5eca1aecf53810e6a8e&"
                +"client_secret=5f57b71ca6b1282d263221ae48a2cd72601896a7&"
                +"code="+code;
        try{
            String access_token = apiService.getToken(get_token_url);
            GitUser user = apiService.getUser(access_token);
            return Result.SetOk(user);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

}
