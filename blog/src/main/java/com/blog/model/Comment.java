package com.blog.model;


import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


public class Comment implements Serializable {

    private Long id;
    private String nickname;
    private String email;
    private String content;
    private boolean adminComment;  //是否为管理员评论

    //头像
    private String avatar;
    private Date createTime;

    private Long blogId;
    private Long parentCommentId;  //父评论id
    private String parentNickname;

    //回复评论
    //private List<Comment> replyComments = new ArrayList<>();

    //父评论
    private Comment parentComment;

    private Blog blog;

    public Comment(Long id, String nickname, String email, String content, boolean adminComment, String avatar, Date createTime, Long blogId, Long parentCommentId, String parentNickname, Comment parentComment, Blog blog) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.content = content;
        this.adminComment = adminComment;
        this.avatar = avatar;
        this.createTime = createTime;
        this.blogId = blogId;
        this.parentCommentId = parentCommentId;
        this.parentNickname = parentNickname;
        this.parentComment = parentComment;
        this.blog = blog;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getParentNickname() {
        return parentNickname;
    }

    public void setParentNickname(String parentNickname) {
        this.parentNickname = parentNickname;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
