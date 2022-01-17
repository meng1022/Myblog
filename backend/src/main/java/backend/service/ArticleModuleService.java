package backend.service;

import java.util.List;

import backend.entity.Article_Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ArticleModuleService {
    @Autowired
    HibernateTemplate hibernateTemplate;

    public void writeArticle(long articleid,List<Integer> moduleids) {
        for(long moduleid:moduleids){
            Article_Module article_module = new Article_Module();
            article_module.setArticleid(articleid);
            article_module.setModuleid(moduleid);
            hibernateTemplate.save(article_module);
        }
    }
}
