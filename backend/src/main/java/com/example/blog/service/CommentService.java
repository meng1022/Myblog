package com.example.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.blog.dao.CommentDao;
import com.example.blog.entity.Comment;
import com.example.blog.entity.CommentObject;
import com.example.blog.utils.MyWebSocket;
import com.example.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentService {
    @Resource
    CommentDao commentDao;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MyWebSocket webSocket;


    public List<CommentObject> getComments(long articleid){
        List<Comment> list = commentDao.findByArticleidAndCommentidOrderByCreateTimeAsc(articleid,0);
        List<CommentObject> ans = new ArrayList<>();
        for(Comment comment:list){
            long commentid = comment.getId();
            List<Comment> comments = commentDao.findByCommentidOrderByCreateTimeAsc(commentid);
            CommentObject object = new CommentObject(comment,comments);
            ans.add(object);
        }
        return ans;
    }

    public Comment addComment(Map<String,Object> comment){
        Comment c = new Comment();
        c.setArticleid(Long.valueOf(comment.get("articleid").toString()));
        c.setCommentid(Long.valueOf(comment.get("commentid").toString()));
        c.setContent((String) comment.get("content"));
        c.setFromuser(Long.valueOf((String) comment.get("fromuser")));
        c.setFromusername((String) comment.get("fromusername"));
        c.setFromavatar((String) comment.get("fromavatar"));
        c.setTouser(Long.valueOf((String) comment.get("to")));
        Comment comment1= commentDao.save(c);
        redisUtil.lPush((String) comment.get("to"),comment1,7);
        webSocket.sendNotification((String) comment.get("to"),redisUtil.lCount((String) comment.get("to")));

        return comment1;
    }

    public void NotificatonCount(long userid){
        int notificationCount = redisUtil.lCount(userid+"");
        webSocket.sendNotification(userid+"",notificationCount);
    }

    public Map<String,Object> getAllNotifications(long userid){
        int notificationCount = redisUtil.lCount(userid+"");
        List<Comment> comments = commentDao.findByTouserOrderByCreateTimeDesc(userid);
        redisUtil.lRemove(userid+"");
        webSocket.sendNotification(userid+"",0);
        Map<String,Object> map = new HashMap<>();
        map.put("notifications",comments.subList(0,notificationCount));
        map.put("comments",comments.subList(notificationCount,comments.size()));
        return map;
    }
}
