package backend.controller;

import backend.Result;
import backend.entity.Article;
import backend.entity.Module;
import backend.entity.User;
import backend.service.ArticleService;
import backend.service.ModuleService;
import backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
//@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ModuleService moduleService;
    @Autowired
    ArticleService articleService;

    public static final String KEY_USER = "__user__";
    final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(RuntimeException.class)
    // catch RuntimeException throwed by this controller and return error page
    public ModelAndView handleUnknownException(Exception ex){
        return new ModelAndView("500.html",
                Map.of("error",ex.getClass().getSimpleName(),"message",ex.getMessage()));
    }

    @GetMapping("/homepage")
    public Result index() {
        try {
            List<Article> articles = articleService.getRecentArticles();
            return Result.SetOk(articles);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @GetMapping("/register")
    public ModelAndView register(){
        return new ModelAndView("register.html");
    }

    @PostMapping("/register")
    public ModelAndView doregister(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("name")String name){
        try{
            userService.register(email,password,name);
        }catch (RuntimeException e){
            ModelAndView mv = new ModelAndView("register.html");
            mv.addObject("error","register failed");
            return mv;
        }
        return new ModelAndView("redirect:/user/signin");
    }

    @GetMapping("/signin")
    public ModelAndView signin(){
        return new ModelAndView("signin.html");
    }

    @PostMapping("/signin")
    public ModelAndView doSignin(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 HttpSession session){
        User user = userService.signin(email,password);
        if(user==null){
            logger.info("login failed");
            ModelAndView mv = new ModelAndView("signin.html");
            mv.addObject("email",email);
            mv.addObject("error","login failed");
            return mv;
        }
        session.setAttribute(KEY_USER,user);
        return new ModelAndView("redirect:/user/");
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpSession session){
        User user = (User) session.getAttribute(KEY_USER);
        if(user==null)
            return new ModelAndView("redirect:/user/signin");
        logger.info(user.getName()+" create at "+user.getCreateTime());
        return new ModelAndView("profile.html",Map.of("user",user));
    }

    @GetMapping("/signout")
    public ModelAndView signout(HttpSession session){
        session.removeAttribute(KEY_USER);
        return new ModelAndView("redirect:/user/");
    }

}
