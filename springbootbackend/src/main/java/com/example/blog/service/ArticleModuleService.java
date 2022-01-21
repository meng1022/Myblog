package com.example.blog.service;

import java.util.List;
import com.example.blog.dao.ArticleModuleDao;
import com.example.blog.entity.Article_Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleModuleService {
    @Resource
    ArticleModuleDao articleModuleDao;

    public void writeArticle(long articleid,List<Integer> moduleids){
        for(long moduleid:moduleids){
            Article_Module article_module = new Article_Module();
            article_module.setArticleid(articleid);
            article_module.setModuleid(moduleid);
            articleModuleDao.save(article_module);
        }
    }

}
