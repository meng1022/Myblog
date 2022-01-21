package com.example.blog.dao;

import java.util.List;

import com.example.blog.entity.Article_Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleModuleDao extends JpaRepository<Article_Module,Long> {

    List<Article_Module> findByArticleid(long articleid);

    List<Article_Module> findByModuleid(long moduleid);


}
