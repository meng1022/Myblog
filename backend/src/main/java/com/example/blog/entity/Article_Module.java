package com.example.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="article_module")
@Entity
public class Article_Module extends AbstractEntity{
    private Long articleid;
    private Long moduleid;

    @Column
    public Long getModuleid(){
        return moduleid;
    }

    public void setModuleid(Long moduleid){
        this.moduleid = moduleid;
    }

    @Column
    public Long getArticleid(){
        return articleid;
    }

    public void setArticleid(Long articleid){
        this.articleid = articleid;
    }


}
