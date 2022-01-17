package backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.entity.Article;
import backend.entity.Module;
import backend.entity.Article_Module;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ArticleService {
    @Autowired
    HibernateTemplate hibernateTemplate;

    public List<Article> getArticles(){
        DetachedCriteria cr = DetachedCriteria.forClass(Article.class);
        cr.addOrder(Order.desc("createTime"));
        List<Article> list = (List<Article>)hibernateTemplate.findByCriteria(cr);
        return list;
    }

    public Map<String,Object> getArticle(long articleid){
        Map<String,Object> ans = new HashMap<>();
        Article article = hibernateTemplate.get(Article.class,articleid);
        Article_Module am = new Article_Module();
        am.setArticleid(articleid);
        List<Article_Module> ams = hibernateTemplate.findByExample(am);
        List<Long> modules = new ArrayList<>();
        List<Module> modulenames = new ArrayList<>();
        for(Article_Module article_module:ams){
            long moduleid = article_module.getModuleid();
            modules.add(moduleid);
            modulenames.add(hibernateTemplate.get(Module.class,moduleid));
        }
        ans.put("article",article);
        ans.put("modules",modules);
        ans.put("modulenames",modulenames);
        return ans;
    }

    public List<Article> getRecentArticles(){
        DetachedCriteria cr = DetachedCriteria.forClass(Article.class);
        cr.addOrder(Order.desc("createTime"));
        return (List<Article>) hibernateTemplate.findByCriteria(cr,0,2);
    }

    public List<Article> getModuleArticles(long moduleid){
        DetachedCriteria cr = DetachedCriteria.forClass(Article_Module.class);
        cr.add(Restrictions.eq("moduleid",moduleid));
        cr.addOrder(Order.desc("createTime"));
        List<Article_Module> article_modules = (List<Article_Module>) hibernateTemplate.findByCriteria(cr);
        List<Article> articles = new ArrayList<>();
        for(Article_Module am:article_modules){
            Article article = hibernateTemplate.get(Article.class,am.getArticleid());
            articles.add(article);
        }
        return articles;
    }

    public long writeArticle(String title,String content){
        Article article = new Article();
        article.setContent(content);
        article.setTitle(title);
        hibernateTemplate.save(article);
        return article.getId();
    }

    public void editarticle(long articleid,String title,List<Long> moduleids,String content){
        DetachedCriteria cr = DetachedCriteria.forClass(Article_Module.class);
        cr.add(Restrictions.eq("articleid",articleid));
        List<Article_Module> ams = (List<Article_Module>)hibernateTemplate.findByCriteria(cr);
        for(Article_Module am:ams){
            long moduleid = am.getModuleid();
            if(!moduleids.contains(moduleid))
                hibernateTemplate.delete(am);
            else
                moduleids.remove(Long.valueOf(moduleid));
        }

        Article article = hibernateTemplate.get(Article.class,articleid);
        article.setTitle(title);
        article.setContent(content);
        hibernateTemplate.update(article);

        for(long mid:moduleids){
            Article_Module article_module = new Article_Module();
            article_module.setArticleid(articleid);
            article_module.setModuleid(mid);
            hibernateTemplate.save(article_module);
        }
    }

    public void deletearticle(long articleid){
        Article article = hibernateTemplate.get(Article.class,articleid);
        if(article!=null)
            hibernateTemplate.delete(article);
    }

    public List<Article> searchArticles(String key){
        DetachedCriteria cr = DetachedCriteria.forClass(Article.class);
        cr.add(Restrictions.like("title",key, MatchMode.ANYWHERE));
        cr.addOrder(Order.desc("createTime"));
       return (List<Article>)hibernateTemplate.findByCriteria(cr);
    }
}
