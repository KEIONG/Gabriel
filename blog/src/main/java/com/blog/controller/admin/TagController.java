package com.blog.controller.admin;


import com.blog.exception.NotFoundException;
import com.blog.model.Tag;
import com.blog.model.User;
import com.blog.service.impl.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    TagService service;

    @GetMapping("/tags")
    public String tags(@RequestParam(required = false, defaultValue = "1", value = "pagenum") int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Tag> allTag = service.getAllTag();
        PageInfo<Tag> pageInfo = new PageInfo<>(allTag);

        model.addAttribute("pageInfo", pageInfo);


        return "admin/tags";

    }

    @GetMapping("/tags/input")
    public String toAddTag(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags/{id}/input")
    public String toEditTag(@PathVariable Long id, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user.getId() != 1){
            return "redirect:/admin";
        }
        model.addAttribute("tag", service.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String addTag(Tag tag, RedirectAttributes attributes){
        Tag t = service.getTagByName(tag.getName());
        if(t != null){
            attributes.addFlashAttribute("msg","不能重复添加");
            return "redirect:/admin/tags/input";
        } else {
            attributes.addFlashAttribute("msg","添加成功");

        }
        service.saveTag(tag);
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editTag(@PathVariable Long id, Tag tag, RedirectAttributes attributes){
        Tag t = service.getTag(id);//  I dont think so
        if(t != null){
            attributes.addFlashAttribute("msg","不能重复添加");
            return "redirect:/admin/tags/input";
        } else {
            attributes.addFlashAttribute("msg","修改成功");
        }
        service.updateTag(tag);
        return "redirect:/admin/tags";
    }

    @GetMapping("tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user.getId() != 1){
            return "redirect:/admin";
        }
        int flag = service.deleteTag(id);
        if(flag > 0){
            attributes.addFlashAttribute("msg","Delete Sucessfully!");

        } else{
            attributes.addFlashAttribute("msg", "Not Found !!");
            throw new NotFoundException();
        }
        return "redirect:/admin/tags";
    }



















}
