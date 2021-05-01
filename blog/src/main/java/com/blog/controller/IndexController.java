package com.blog.controller;


import com.blog.model.Blog;
import com.blog.model.Tag;
import com.blog.model.Type;
import com.blog.service.impl.BlogService;
import com.blog.service.impl.TagService;
import com.blog.service.impl.TypeService;
import com.blog.util.RedisPageHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    TagService tagService;

    @Autowired
    TypeService typeService;

    @Autowired
    BlogService blogService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedisPageHelper redisPageHelper;

    @GetMapping("/")
    public String toIndex(@RequestParam(required = false, defaultValue = "1", value = "pagenum")int pagenum, Model model, HttpSession session){

        ValueOperations<String, String> operations = redisTemplate.opsForValue();



        long start = System.currentTimeMillis();

        System.out.println(pagenum);
        PageHelper.startPage(pagenum, 6);
        List<Blog> allBlog = blogService.getIndexBlog();
        List<Type> allType = typeService.getBlogType();
        List<Tag> allTag = tagService.getBlogTag();
        List<Blog> recommendBlog = blogService.getAllRecommendBlog();
        PageInfo pageInfo = new PageInfo(allBlog);
        System.out.println(pageInfo.isHasNextPage());
//        pageInfo.setPageNum(pagenum);
//        for(Blog blog: allBlog){
//            System.out.println(blog);
//        }





        String sum = operations.get("/");

        session.setAttribute("sum", sum);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendBlogs", recommendBlog);
        long end = System.currentTimeMillis();
        System.out.println("program running time :" + (end - start + 1));
        System.out.println(pageInfo.getPageNum());

        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "1", value = "pagenum") int pagenum, @RequestParam String query, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Blog> searchBlog = blogService.getSearchBlog(query);
        PageInfo pageInfo = new PageInfo(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String toLogin(@PathVariable Long id, Model model){
        System.out.println("!!!!");
        Blog blog = blogService.getDetailedBlog(id);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String tmp = "blog" + id.toString();
        operations.increment(tmp);
        model.addAttribute("blogSum", operations.get(tmp));
        model.addAttribute("blog", blog);
        return "blog";
    }

}
