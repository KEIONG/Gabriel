package com.blog.model;


import org.springframework.stereotype.Component;

import java.io.Serializable;


public class BlogAndTag implements Serializable {

    private Long tagId;
    private Long blogId;

    public BlogAndTag(Long tagId, Long blogId) {
        this.tagId = tagId;
        this.blogId = blogId;
    }

    public BlogAndTag() {
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
}
