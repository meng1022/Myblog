package com.example.blog.controller;

import com.example.blog.Result;
import com.example.blog.entity.GitUser;
import com.example.blog.service.GitUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitUserController {
    @Autowired
    GitUserService gitUserService;

    @GetMapping("/api/getUserinfo")
    public Result getUserinfo(@RequestParam("code")String code){
        String get_token_url = "https://github.com/login/oauth/access_token?client_id=a5eca1aecf53810e6a8e&"
                +"client_secret=5f57b71ca6b1282d263221ae48a2cd72601896a7&"
                +"code="+code;
        try{
            String access_token = gitUserService.getToken(get_token_url);
            GitUser user = gitUserService.getUser(access_token);
            return Result.SetOk(user);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }
}
