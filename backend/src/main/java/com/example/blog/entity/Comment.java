package com.example.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="comment")
public class Comment extends AbstractEntity{
    private Long articleid;
    private Long commentid;
    private Long fromuser;
    private String fromusername;
    private String fromavatar;
    private Long touser;
    private String content;

    @Column
    public Long getArticleid() {
        return articleid;
    }
    public void setArticleid(Long articleid) {
        this.articleid = articleid;
    }

    @Column
    public Long getCommentid() {
        return commentid;
    }
    public void setCommentid(Long commentid) {
        this.commentid = commentid;
    }

    @Column
    public Long getFromuser() {
        return fromuser;
    }
    public void setFromuser(Long fromuser) {
        this.fromuser = fromuser;
    }

    @Column
    public String getFromusername() {
        return fromusername;
    }
    public void setFromusername(String fromusername) {
        this.fromusername = fromusername;
    }

    @Column
    public String getFromavatar() {
        return fromavatar;
    }
    public void setFromavatar(String fromavatar) {
        this.fromavatar = fromavatar;
    }

    @Column
    public Long getTouser() {
        return touser;
    }
    public void setTouser(Long touser) {
        this.touser = touser;
    }


    @Column
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
