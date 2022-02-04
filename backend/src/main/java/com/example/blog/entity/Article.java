package com.example.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="articles")
@Entity
public class Article extends AbstractEntity{
    private String title;
    private String content;

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
