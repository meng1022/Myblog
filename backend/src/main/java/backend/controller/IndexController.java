package backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @GetMapping("/aboutme")
    public ModelAndView getmyinfo(){
        return new ModelAndView("aboutme.html");
    }

    @GetMapping("/projects")
    public ModelAndView getmyproj(){
        return new ModelAndView("projects.html");
    }

    @GetMapping("/experience")
    public ModelAndView getexperience(){
        return new ModelAndView("experience.html");
    }
}
