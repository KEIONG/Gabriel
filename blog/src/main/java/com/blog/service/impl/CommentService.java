package com.blog.service.impl;


import com.blog.mapper.BlogMapper;
import com.blog.mapper.CommentMapper;
import com.blog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("CommentService")
public class CommentService  {

    @Autowired
    private CommentMapper commentDao;

    @Autowired
    private BlogMapper blogDao;

//   @Cacheable(value = {"CommentById"}, key = "#blogId")
    public List<Comment> getCommentById(Long blogId){
        List<Comment> comments = commentDao.findByBlogIdAndParentCommentNull(blogId, Long.parseLong("-1"));
        return comments;
    }

    public int saveComment(Comment comment){
        Long parentComentId = comment.getParentComment().getId();
        if(parentComentId != -1){
            comment.setParentComment(commentDao.findByParentCommentId(comment.getParentCommentId()));
        } else {
            comment.setParentCommentId((long) -1);
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.saveComment(comment);

    }






}
