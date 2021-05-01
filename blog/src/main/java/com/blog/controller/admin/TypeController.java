package com.blog.controller.admin;


import com.blog.exception.NotFoundException;
import com.blog.model.Type;
import com.blog.model.User;
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
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService service;

    @GetMapping("/types")
    public String types(@RequestParam(required = false, defaultValue = "1" ,value = "pagenum") int pagenum, Model model){
        PageHelper.startPage(pagenum,5);
        List<Type> allType = service.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<>(allType);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String toAddType(Model model){
//        System.out.println("!!!!");
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String toEditType(@PathVariable Long id, Model model){
        model.addAttribute("type", service.getType(id));
        return "admin/types-input";

    }

    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes){
        Type tmp = service.getTypeByName(type.getName());
        if(tmp != null){
            attributes.addFlashAttribute("msg", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        } else {
            attributes.addFlashAttribute("msg", "添加成功");
        }
        service.saveType(type);
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editType(@PathVariable Long id, Type type, RedirectAttributes attributes, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user.getId() != 1){
            return "redirect:/admin";
        }
        Type tmp = service.getTypeByName(type.getName());
        if(tmp != null){
            attributes.addFlashAttribute("msg", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        } else {
            attributes.addFlashAttribute("msg", "修改成功！");
        }
        service.saveType(type);
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user.getId() != 1){
            return "redirect:/admin";
        }
        int flag = service.deleteType(id);
        if(flag > 0){
            attributes.addFlashAttribute("msg","delete successfully!!");
        } else {
            attributes.addFlashAttribute("msg","delete frustated!");
            throw new NotFoundException();
        }
        return "redirect:/admin/types";

    }




}
