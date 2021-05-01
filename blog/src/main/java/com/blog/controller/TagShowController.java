package com.blog.controller;


import com.blog.model.Blog;
import com.blog.model.Tag;
import com.blog.service.impl.BlogService;
import com.blog.service.impl.TagService;
import com.blog.service.impl.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    TagService tagService;

    @Autowired
    BlogService blogService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id, @RequestParam(required = false, defaultValue = "1", value = "pagenum")int pagenum, Model model){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String sum = operations.get("/");

        model.addAttribute("sum", sum);
        PageHelper.startPage(pagenum, 100);
        List<Tag> tags = tagService.getBlogTag();


        if(id == -1){
            id = tags.get(0).getId();
        }
        List<Blog> blogs = blogService.getByTagId(id);
        for(Blog blog: blogs){
            System.out.println(blog);

        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("tags", tags);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTagId", id);

        return "tags";



    }


}
