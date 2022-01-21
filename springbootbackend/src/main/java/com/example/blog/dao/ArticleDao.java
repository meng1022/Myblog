package com.example.blog.dao;

import java.util.List;

import com.example.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDao extends JpaRepository<Article,Long> {

    List<Article> findTop2ByOrderByCreateTimeDesc();

    List<Article> findAllByOrderByCreateTimeDesc();

    List<Article> findByTitleContains(String title);

}
