package com.blog.mapper;


import com.blog.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    List<Comment> findByBlogIdAndParentCommentNull(@Param("blogId")Long blogId, @Param("blogParentId")Long blogParentId);

    Comment findByParentCommentId(@Param("parentCommentId") Long parentCommentId);

    int saveComment(Comment comment);


}
