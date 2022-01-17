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
public class ModuleController {
    @Autowired
    ModuleService moduleService;
    @Autowired
    ArticleService articleService;
    public static final String KEY_USER = "__user__";

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleUnknownException(Exception ex){
        return new ModelAndView("500.html",
                Map.of("error",ex.getClass().getSimpleName(),"message",ex.getMessage()));
    }
//    @PostMapping("/createmodule")
//    public ModelAndView create(@RequestParam("modulename")String modulename,
//                               HttpSession session){
//        User user = (User)session.getAttribute(KEY_USER);
//        if(user==null||user.getId()!=1)
//            throw new RuntimeException("Sorry, you have no authorization");
//        try{
//            moduleService.createmodule(modulename);
//        }catch (RuntimeException e){
//            return new ModelAndView("createmodule.html",Map.of("error",e.getMessage()));
//        }
//        return new ModelAndView("redirect:/getmodules");
//    }

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

    @GetMapping("/getmodules")
    public Result getmodules(HttpSession session){
        try{
            List<Module> list = moduleService.getmodules();
            return Result.SetOk(list);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @GetMapping("/getmodulearticles")
    public Result getarticles(@RequestParam("moduleid")long moduleid){
        try{
            List<Article> articles = articleService.getModuleArticles(moduleid);
            return Result.SetOk(articles);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }
}
