package com.example.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="gituser")
@Entity
public class GitUser {
    private Long userid;
    private String username;
    private String avatar;

    public GitUser(){}

    public GitUser(Long userid, String username,String avatar){
        this.username = username;
        this.userid = userid;
        this.avatar = avatar;
    }

    @Id
    @Column(nullable = false,updatable = false)
    public Long getUserid(){
        return userid;
    }
    public void setUserid(Long userid){
        this.userid = userid;
    }

    @Column(nullable = false)
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    @Column
    public String getAvatar(){
        return avatar;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
}
