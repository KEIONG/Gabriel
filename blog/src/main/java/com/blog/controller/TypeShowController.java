package com.blog.controller;


import com.blog.model.Blog;
import com.blog.model.Type;
import com.blog.service.impl.BlogService;
import com.blog.service.impl.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.media.sound.MidiOutDeviceProvider;
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
public class TypeShowController {

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @RequestParam(required = false, defaultValue = "1", value = "pagenum") int pagenum, Model model){


        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String sum = operations.get("/");

        model.addAttribute("sum", sum);


        PageHelper.startPage(pagenum, 100);
        List<Type> types = typeService.getBlogType();
        if(id == -1){
            id = types.get(0).getId();
        }
        List<Blog> blogs = blogService.getByTypeId(id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

        model.addAttribute("types", types);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTypeId", id);

        return "types";
    }

}
