package com.example.blog.service;

import java.util.*;

import com.example.blog.dao.ArticleDao;
import com.example.blog.dao.ArticleModuleDao;
import com.example.blog.dao.ModuleDao;
import com.example.blog.entity.Article;
import com.example.blog.entity.Article_Module;
import com.example.blog.entity.Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleService {
    @Resource
    ArticleDao articleDao;
    @Resource
    ArticleModuleDao articleModuleDao;
    @Resource
    ModuleDao moduleDao;

    public List<Article> getRecentArticles(){
        return articleDao.findTop2ByOrderByCreateTimeDesc();
    }

    public List<Article> getArticles(){
        return articleDao.findAllByOrderByCreateTimeDesc();
    }

    public Map<String,Object> getArticle(long articleid){
        Map<String,Object> ans = new HashMap<>();
        Article article = articleDao.findById(articleid).orElse(null);

        List<Article_Module> ams = articleModuleDao.findByArticleid(articleid);

        List<Long> modules = new ArrayList<>();
        List<Module> modulenames = new ArrayList<>();

        for(Article_Module article_module:ams){
            long moduleid = article_module.getModuleid();
            modules.add(moduleid);
            modulenames.add(moduleDao.findById(moduleid).orElse(null));
        }

        ans.put("article",article);
        ans.put("modules",modules);
        ans.put("modulenames",modulenames);
        return ans;
    }

    public List<Article> getModuleArticles(long moduleid){
        List<Article_Module> ams = articleModuleDao.findByModuleid(moduleid);
        List<Article> articles = new ArrayList<>();
        for(Article_Module am:ams){
            articles.add(articleDao.findById(am.getArticleid()).orElse(null));
        }
        return articles;
    }

    public long writeArticle(String title,String content){
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        return articleDao.save(article).getId();
    }

    public void editArticle(long articleid,String title,List<Long> moduleids,String content){
        List<Article_Module> ams = articleModuleDao.findByArticleid(articleid);
        for(Article_Module am:ams){
            long moduleid = am.getModuleid();
            if(!moduleids.contains(moduleid))
                articleModuleDao.delete(am);
            else
                moduleids.remove(Long.valueOf(moduleid));
        }

        Article article = articleDao.findById(articleid).orElse(null);
        article.setTitle(title);
        article.setContent(content);
        articleDao.save(article);

        for(long mid:moduleids){
            Article_Module article_module = new Article_Module();
            article_module.setArticleid(articleid);
            article_module.setModuleid(mid);
            articleModuleDao.save(article_module);
        }
    }

    public void deletearticle(long articleid){
        articleDao.deleteById(articleid);
    }

    public List<Article> searchArticles(String key){
        return articleDao.findByTitleContains(key);
    }

}
