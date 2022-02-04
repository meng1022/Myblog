package com.example.blog.dao;

import java.util.List;
import com.example.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment,Long> {

    List<Comment> findByArticleidAndCommentidOrderByCreateTimeAsc(long articleid,long commentid);

    List<Comment> findByCommentidOrderByCreateTimeAsc(long commentid);

    List<Comment> findByTouserOrderByCreateTimeDesc(long touser);
}
