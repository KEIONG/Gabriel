package com.blog.controller;


import com.blog.service.impl.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {

    @Autowired
    BlogService service;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/archives")
    public String archieves(Model model){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String sum = operations.get("/");

        model.addAttribute("sum", sum);
        model.addAttribute("archiveMap", service.archiveBlog());
        model.addAttribute("countBlog", service.countBlog());
        return "archives";
    }





}
