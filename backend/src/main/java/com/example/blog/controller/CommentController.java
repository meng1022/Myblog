package com.example.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.example.blog.Result;
import com.example.blog.entity.Comment;
import com.example.blog.entity.CommentObject;
import com.example.blog.service.CommentService;
import com.example.blog.utils.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/api/getcomments")
    public Result getComments(@RequestParam("articleid")long articleid){
        try{
            List<CommentObject> comments = commentService.getComments(articleid);

            return Result.SetOk(comments);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @PostMapping("/api/addleadcomment")
    public Result addLeadComment(@RequestBody Map<String,Object> map){
        try{
            Comment lead = commentService.addComment(map);
            CommentObject object = new CommentObject(lead,new ArrayList<>());
            return Result.SetOk(object);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @PostMapping("/api/addsubcomment")
    public Result addSubComment(@RequestBody Map<String,Object> map){
        try{
            Comment sub = commentService.addComment(map);
            return Result.SetOk(sub);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @GetMapping("/api/getnotificationcount")
    public Result getNotifications10(@RequestParam("userid")Long userid){
        try{
            commentService.NotificatonCount(userid);
            return Result.SetOk("ok");
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

    @GetMapping("/api/getallnotifications")
    public Result getAllNotifications(@RequestParam("userid")Long userid){
        try{
            Map<String,Object> map = commentService.getAllNotifications(userid);
            return Result.SetOk(map);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }

}
