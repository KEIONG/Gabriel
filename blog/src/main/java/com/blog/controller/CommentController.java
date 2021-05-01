package com.blog.controller;

import com.blog.model.Comment;
import com.blog.model.User;
import com.blog.service.impl.BlogService;
import com.blog.service.impl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    BlogService blogService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String sum = operations.get("/");

        model.addAttribute("sum", sum);
//        System.out.println("??????");
        model.addAttribute("comments", commentService.getCommentById(blogId));
        model.addAttribute("blog", blogService.getDetailedBlog(blogId));

        return "blog::commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
//        System.out.println("!!!!?????");
        System.out.println(comment);


        Long blogId = comment.getBlog().getId();
//        System.out.println(blogId);
        comment.setBlog(blogService.getDetailedBlog(blogId));

        System.out.println("@@@@@@@");
        comment.setBlogId(blogId);
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
//        System.out.println(comment);
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }



}
