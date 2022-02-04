package com.example.blog.entity;

import java.util.List;

public class CommentObject {
    private Comment leadComment;
    private List<Comment> comments;

    public CommentObject(){
    }
    public CommentObject(Comment leadComment, List<Comment> comments){
        this.leadComment = leadComment;
        this.comments = comments;
    }

    public Comment getLeadComment() {
        return leadComment;
    }
    public void setLeadComment(Comment leadComment) {
        this.leadComment = leadComment;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
