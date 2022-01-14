package backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.Result;
import backend.entity.Article;
import backend.entity.Module;
import backend.entity.User;
import backend.service.ArticleService;
import backend.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ModuleService moduleService;

    public static final String KEY_USER = "__user__";
    public static final int PageSize = 10;

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleUnknownException(Exception ex){
        return new ModelAndView("500.html",
                Map.of("error",ex.getClass().getSimpleName(),"message",ex.getMessage()));
    }

//    @GetMapping("/getarticles")
//    public ModelAndView getArticles(@RequestParam("pageidx")int pageidx){
//        List<Article> list = articleService.getArticles();
//        int pagenum = list.size()/PageSize+((list.size()%PageSize==0)?0:1);
//        ModelAndView mv = new ModelAndView("articlelist.html");
//        int lastarticle = (pageidx*PageSize<=list.size())?pageidx*PageSize:list.size();
//        mv.addObject("articles",list.subList((pageidx-1)*PageSize,lastarticle));
//        mv.addObject("pagenum",pagenum);
//        mv.addObject("curpage",pageidx);
//        return mv;
//    }

    @GetMapping("/getarticles")
    public Result getArticles(){
        try {
            List<Article> list = articleService.getArticles();
//            List<Module> modules = moduleService.getmodules();
//            Map<String,Object> map = new HashMap<>();
//            map.put("modules",modules);
//            map.put("articles",list);
//            return Result.SetOk(map);
            return Result.SetOk(list);
        }catch (Exception e) {
            return Result.SetError(e.getMessage());
        }
    }

//    @GetMapping("/getarticle")
//    public ModelAndView getarticle(@RequestParam("articleid")long articleid,
//                                   HttpSession session){
//        Article article = articleService.getArticle(articleid);
//        Module module = moduleService.getModule(article.getModuleid());
//        ModelAndView mv = new ModelAndView("article.html",Map.of("article",article,"module",module));
//        User user = (User) session.getAttribute(KEY_USER);
//        if(user!=null)
//            mv.addObject("user",user);
//        return mv;
//    }
    @GetMapping("/getarticle")
    public Result getarticle(@RequestParam("articleid")long articleid,
                                   HttpSession session){
        try{
            Article article = articleService.getArticle(articleid);
            return Result.SetOk(article);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
//        Module module = moduleService.getModule(article.getModuleid());
    }

    @GetMapping("/writearticle")
    public ModelAndView writearticle(HttpSession session){
        User user = (User)session.getAttribute(KEY_USER);
        if(user!=null&&user.getId()==1){
            List<Module> modules = moduleService.getmodules();
            return new ModelAndView("writearticle.html",Map.of("modules",modules));
        }
        else
            throw new RuntimeException("Sorry, you have no authorization");
    }


    @PostMapping("/writearticle")
    public ModelAndView writearticle(@RequestParam("title")String title,
                                     @RequestParam("content")String content,
                                     @RequestParam("moduleid")long moduleid,
                                     HttpSession session){
        User user = (User)session.getAttribute(KEY_USER);
        if(user!=null&&user.getId()==1) {
            articleService.writeArticle(title,content,moduleid);
            return new ModelAndView("redirect:/getarticles?pageidx=1");
        }
        else
            throw new RuntimeException("Sorry, you have no authorization");
    }

    @GetMapping("/editarticle")
    public ModelAndView editarticle(@RequestParam("articleid")long articleid,
                                    HttpSession session){
        User user = (User)session.getAttribute(KEY_USER);
        if(user!=null&&user.getId()==1) {
            List<Module> modules = moduleService.getmodules();
            Article article = articleService.getArticle(articleid);
            return new ModelAndView("editarticle.html",Map.of("article",article,
                                                                        "modules",modules));
        }
        else
            throw new RuntimeException("Sorry, you have no authorization");
    }

    @PostMapping("/editarticle")
    public ModelAndView editarticle(@RequestParam("moduleid")long moduleid,
                                    @RequestParam("title")String title,
                                    @RequestParam("content")String content,
                                    @RequestParam("articleid")long articleid,
                                    HttpSession session){
        User user = (User)session.getAttribute(KEY_USER);
        if(user!=null&&user.getId()==1) {
            articleService.editarticle(articleid,moduleid,title,content);
            return new ModelAndView("redirect:/getarticle?articleid="+articleid);
        }
        else
            throw new RuntimeException("Sorry, you have no authorization");
    }

    @PostMapping("/deletearticle")
    public ModelAndView deletearticle(@RequestParam("articleid")long articleid,
                                      HttpSession session){
        User user = (User)session.getAttribute(KEY_USER);
        if(user!=null&&user.getId()==1) {
            articleService.deletearticle(articleid);
            return new ModelAndView("redirect:/getarticles");
        }
        else
            throw new RuntimeException("Sorry, you have no authorization");
    }

    @PostMapping("/searcharticles")
    public ModelAndView search(@RequestParam("searchkey")String key){
        List<Article> list = articleService.searchArticles(key);
        ModelAndView mv = new ModelAndView("searchresult.html");
        mv.addObject("articles",list);
        return mv;
    }
}
