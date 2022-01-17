package backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="article_module")
public class Article_Module extends AbstractEntity{
    private Long articleid;
    private Long moduleid;

    @Column(nullable = false)
    public Long getModuleid(){
        return moduleid;
    }

    public void setModuleid(Long moduleid){
        this.moduleid = moduleid;
    }

    @Column(nullable = false)
    public Long getArticleid(){
        return articleid;
    }

    public void setArticleid(Long articleid){
        this.articleid = articleid;
    }


}
