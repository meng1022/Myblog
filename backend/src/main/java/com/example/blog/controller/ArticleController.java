package com.example.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.blog.Result;
import com.example.blog.entity.Article;
import com.example.blog.service.ArticleModuleService;
import com.example.blog.service.ArticleService;
import com.example.blog.service.ModuleService;
import com.example.blog.utils.imgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleModuleService articleModuleService;
    @Autowired
    ModuleService moduleService;
    @Autowired
    imgUtil util;

    @GetMapping("/api/homepage")
    public Result index() {
        try {
            List<Article> articles = articleService.getRecentArticles();
            return Result.SetOk(articles);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @GetMapping("/api/getarticles")
    public Result getArticles(){
        try {
            List<Article> list = articleService.getArticles();
            return Result.SetOk(list);
        }catch (Exception e) {
            return Result.SetError(e.getMessage());
        }
    }

    @GetMapping("/api/getarticle")
    public Result getArticle(@RequestParam("articleid")long articleid){
        try{
            //"article":article; "modules":moduleids; modulenames:modules;
            Map<String,Object> article = articleService.getArticle(articleid);
            return Result.SetOk(article);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @PostMapping("/writearticle")
    public Result writeArticle(@RequestBody Map newArticle){
        String title = (String) newArticle.get("title");
        String content = (String) newArticle.get("content");
        List<Integer> moduleids = (List<Integer>) newArticle.get("moduleids");
        try{
            long articleid = articleService.writeArticle(title,content);
            articleModuleService.writeArticle(articleid,moduleids);
            return Result.SetOk("200 ok");
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @GetMapping("/editarticle")
    public Result editarticle(@RequestParam("articleid")long articleid){
        Map<String,Object> ans = new HashMap<>(articleService.getArticle(articleid));
        ans.put("totalModules",moduleService.getmodules());
        return Result.SetOk(ans);
    }

    @PostMapping("/editarticle")
    public Result editarticle(@RequestBody Map editedArticle){
        try{
            long articleid =  Long.valueOf((String) editedArticle.get("articleid"));
            String title = (String) editedArticle.get("title");
            List<Long> moduleids = JSONArray.parseArray(editedArticle.get("moduleids").toString(),Long.class);
            String content = (String) editedArticle.get("content");
            articleService.editArticle(articleid,title,moduleids,content);
            return Result.SetOk("200 ok");
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @PostMapping("/deletearticle")
    public Result deletearticle(@RequestBody Map map){
        try{
            long articleid = Long.valueOf(map.get("articleid").toString());
            articleService.deletearticle(articleid);
            return Result.SetOk("200 ok");
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @PostMapping("/api/searcharticles")
    public Result search(@RequestBody Map map){
        try{
            String key = (String) map.get("search");
            List<Article> list = articleService.searchArticles(key);
            return Result.SetOk(list);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @PostMapping("/api/uploadImg")
    public Result storeImg(@RequestParam("myImage") MultipartFile img){
        if(img==null)
            return Result.SetOk("no picture uploaded");
        else{
            try{
                String imgname = util.uploadImg(img);
                return Result.SetOk(imgname);
            }catch (Exception e){
                return Result.SetError(e.getMessage());
            }
        }
    }

    @GetMapping("/api/getImg/{imgName}")
    public ResponseEntity<?> getImg(@PathVariable String imgName){
        try{
            return ResponseEntity.ok(util.getImg(imgName));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
