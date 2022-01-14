package backend.service;

import java.util.List;
import backend.entity.Article;
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

    public Article getArticle(long articleid){
        return hibernateTemplate.get(Article.class,articleid);
    }

    public List<Article> getRecentArticles(){
        DetachedCriteria cr = DetachedCriteria.forClass(Article.class);
        cr.addOrder(Order.desc("createTime"));
        return (List<Article>) hibernateTemplate.findByCriteria(cr,0,2);
    }

    public List<Article> getModuleArticles(long moduleid){
        Article example = new Article();
        example.setModuleid(moduleid);
        return hibernateTemplate.findByExample(example);
    }

    public void writeArticle(String title,String content,long moduleid){
        Article article = new Article();
        article.setUserid(Long.valueOf(1));
        article.setContent(content);
        article.setTitle(title);
        article.setModuleid(moduleid);
        hibernateTemplate.save(article);
    }

    public void editarticle(long articleid,long moduleid,String title,String content){
        Article article = hibernateTemplate.get(Article.class,articleid);
        article.setModuleid(moduleid);
        article.setTitle(title);
        article.setContent(content);
        hibernateTemplate.update(article);
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
