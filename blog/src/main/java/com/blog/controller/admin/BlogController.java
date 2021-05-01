package com.blog.controller.admin;


import com.blog.exception.NotFoundException;
import com.blog.model.Blog;
import com.blog.model.User;
import com.blog.service.impl.BlogService;
import com.blog.service.impl.TagService;
import com.blog.service.impl.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    public void setTypeAndTag(Model model){
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
    }

    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false, defaultValue = "1", value = "pagenum") int pagenum, Model model, HttpSession session){

//        System.out.println("!!!!");
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();
//        System.out.println(userId);
//        List<Blog>blogList = blogService.getByUserId(userId);
//        System.out.println(blogList);

        PageHelper.startPage(pagenum, 5);
        List<Blog> allBlog = blogService.getByUserId(userId);
//        System.out.println(allBlog);
//        List<Blog> allBlog = blogService.getAllBlog();
//        System.out.println(allBlog);
//        System.out.println(pagenum + "????");
//        System.out.println("!!!!??????");
//        Iterator<Blog> iter = allBlog.iterator();
//        while (iter.hasNext()) {
//            Blog item = iter.next();
//            System.out.println(item.getUserId());
//            if(item.getUserId() != userId){
//                iter.remove();
//                System.out.println("++++");
//            }
//        }
//        while (iter.hasNext()) {
//            Blog item = iter.next();
//            System.out.println(item.getTitle());
//        }
//        for(Blog blog: allBlog){
//            System.out.println(blog.getTitle());
//        }

//        for(Blog blog: allBlog){
//            System.out.println(blog.getTitle());
//            System.out.println(blog.getUpdateTime());
//        }



        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String searchBlogs(Blog blog, @RequestParam(required = false, defaultValue = "1", value = "pagenum") int pagenum, Model model){
        System.out.println("!!!???");
        PageHelper.startPage(pagenum, 5);
        List<Blog> allBlog = blogService.searchAllBlog(blog);

        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("message", "查询成功");
        return "admin/blogs";

    }


    @GetMapping("/blogs/input")
    public String toAddBlog(Model model){
        model.addAttribute("blog", new Blog());
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/input")
    public String toEditBlog(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlog(id);
        blog.init();
//        System.out.println("--------" + blog.getTypeId());
        model.addAttribute("blog", blog);
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes){

        blog.setUser((User) session.getAttribute("user"));

        blog.setUserId(blog.getUser().getId());

        blog.setType(typeService.getType(blog.getType().getId()));

        blog.setTypeId(blog.getType().getId());

        blog.setTags(tagService.getTagByString(blog.getTagIds()));

        if(blog.getId() == null){
            blogService.savaBlog(blog);

        } else {

            blogService.updateBlog(blog);

        }

        attributes.addFlashAttribute("msg","新增成功");


        return "redirect:/admin/blogs";



    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlogs(@PathVariable Long id, RedirectAttributes attributes, HttpSession session){

        User user = (User) session.getAttribute("user");
//        System.out.println(user.getUsername() + "!!!");
//        System.out.println(user.getId());
//        Long userId = blogService.getBlog(id).getUserId();
        Blog tmp = blogService.getBlog(id);
        Long userId = tmp.getUserId();
//        System.out.println(tmp);


        if(user.getId() != userId){
//            System.out.println("????");
//            System.out.println(userId);
//            attributes.addFlashAttribute("msg","删除失败！");
            throw new NotFoundException();
        }
        int flag = blogService.deleteBlog(id);
        if(flag == 1){
            attributes.addFlashAttribute("msg","删除成功！");
        } else {

            attributes.addFlashAttribute("msg","删除失败！");
            throw new NotFoundException();
        }

        return "redirect:/admin/blogs";

    }








}
