package com.example.blog.controller;

import com.example.blog.Result;
import com.example.blog.entity.Article;
import com.example.blog.entity.Module;
import com.example.blog.service.ArticleService;
import com.example.blog.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class ModuleController {
    @Autowired
    ModuleService moduleService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/getmodules")
    public Result getmodules(){
        try{
            List<Module> list = moduleService.getmodules();
            return Result.SetOk(list);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @GetMapping("/api/getmodulearticles")
    public Result getarticles(@RequestParam("moduleid")long moduleid){
        try{
            List<Article> articles = articleService.getModuleArticles(moduleid);
            return Result.SetOk(articles);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @PostMapping("/createmodule")
    public Result create(@RequestBody Map module){
        try{
            String modulename = (String) module.get("modulename");
            moduleService.createmodule(modulename);
            return Result.SetOk("200 ok");
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }


}
