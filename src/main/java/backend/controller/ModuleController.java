package backend.controller;

import java.util.List;
import java.util.Map;

import backend.entity.Article;
import backend.entity.Module;
import backend.entity.User;
import backend.service.ArticleService;
import backend.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
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

    @GetMapping("/createmodule")
    public ModelAndView create(HttpSession session){
        User user = (User)session.getAttribute(KEY_USER);
        if(user!=null&&user.getId()==1)
            return new ModelAndView("createmodule.html",Map.of("user",user));
        else
            throw new RuntimeException("Sorry, you have no authorization");
    }

    @PostMapping("/createmodule")
    public ModelAndView create(@RequestParam("modulename")String modulename,
                               HttpSession session){
        User user = (User)session.getAttribute(KEY_USER);
        if(user==null||user.getId()!=1)
            throw new RuntimeException("Sorry, you have no authorization");
        try{
            moduleService.createmodule(modulename);
        }catch (RuntimeException e){
            return new ModelAndView("createmodule.html",Map.of("error",e.getMessage()));
        }
        return new ModelAndView("redirect:/getmodules");
    }

    @GetMapping("/getmodules")
    public ModelAndView getmodules(HttpSession session){
        List<Module> list = moduleService.getmodules();
        ModelAndView mv = new ModelAndView("modulelist.html");
        mv.addObject("modules",list);
        User user = (User) session.getAttribute(KEY_USER);
        mv.addObject("user",user);
        return mv;
    }

    @GetMapping("/getmodulearticles")
    public ModelAndView getarticles(@RequestParam("moduleid")long moduleid){
        List<Article> articles = articleService.getModuleArticles(moduleid);
        return new ModelAndView("searchresult.html",Map.of("articles",articles));
    }
}
