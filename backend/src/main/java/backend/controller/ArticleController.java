package backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.Result;
import backend.entity.Article;
import backend.entity.Module;
import backend.entity.User;
import backend.service.ArticleModuleService;
import backend.service.ArticleService;
import backend.service.ModuleService;
import com.alibaba.fastjson.JSONArray;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ModuleService moduleService;
    @Autowired
    ArticleModuleService articleModuleService;

    public static final String KEY_USER = "__user__";
    public static final int PageSize = 10;

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleUnknownException(Exception ex){
        return new ModelAndView("500.html",
                Map.of("error",ex.getClass().getSimpleName(),"message",ex.getMessage()));
    }

    @GetMapping("/getarticles")
    public Result getArticles(){
        try {
            List<Article> list = articleService.getArticles();
            return Result.SetOk(list);
        }catch (Exception e) {
            return Result.SetError(e.getMessage());
        }
    }

    @GetMapping("/getarticle")
    public Result getarticle(@RequestParam("articleid")long articleid,
                                   HttpSession session){
        try{
            //"article":article; "modules":moduleids; modulenames:modules;
            Map<String,Object> article = articleService.getArticle(articleid);
            return Result.SetOk(article);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @PostMapping("/writearticle")
    public Result writearticle(@RequestBody Map newArticle,HttpSession session){
        String title = (String) newArticle.get("title");
        String content = (String) newArticle.get("content");
        List<Integer> moduleids = (List<Integer>) newArticle.get("moduleids");
        try{
            long articleid = articleService.writeArticle(title,content);
            articleModuleService.writeArticle(articleid,moduleids);
            return Result.SetOk(null);
        }catch (Exception e){
            return Result.SetError("unable to insert into db");
        }

    }

    @GetMapping("/editarticle")
    public Result editarticle(@RequestParam("articleid")long articleid,
                              HttpSession session){
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
            articleService.editarticle(articleid,title,moduleids,content);
            return Result.SetOk("200 ok");
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }

    }

//    @PostMapping("/deletearticle")
//    public ModelAndView deletearticle(@RequestParam("articleid")long articleid,
//                                      HttpSession session){
//        User user = (User)session.getAttribute(KEY_USER);
//        if(user!=null&&user.getId()==1) {
//            articleService.deletearticle(articleid);
//            return new ModelAndView("redirect:/getarticles");
//        }
//        else
//            throw new RuntimeException("Sorry, you have no authorization");
//    }

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

//    @PostMapping("/searcharticles")
//    public ModelAndView search(@RequestParam("searchkey")String key){
//        List<Article> list = articleService.searchArticles(key);
//        ModelAndView mv = new ModelAndView("searchresult.html");
//        mv.addObject("articles",list);
//        return mv;
//    }

    @PostMapping("/searcharticles")
    public Result search(@RequestBody Map map){
        try{
            String key = (String) map.get("search");
            List<Article> list = articleService.searchArticles(key);
            return Result.SetOk(list);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

}
