package backend.controller;

import backend.entity.User;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Replace @Controller with @RestController
// do not need to write consumes... produces...
@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public User getuser(@PathVariable("id")long id){
        User user = userService.getUserById(id);
        return user;
    }



}
