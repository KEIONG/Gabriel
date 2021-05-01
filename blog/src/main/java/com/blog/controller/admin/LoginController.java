package com.blog.controller.admin;


import com.blog.model.User;
import com.blog.service.impl.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService service;

    @GetMapping()
    public String loginPage(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            return "admin/index";
        }


        return "admin/login";

    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes){
        session.removeAttribute("user");

//        User user = service.checkUser(username, password);
//        if(user != null){
//            user.setPassword(null);
//            session.setAttribute("user", user);
//            return "admin/index";
//        } else{
//            attributes.addFlashAttribute("msg","用户名或密码错误！");
//            return "redirect:/admin";
//        }
        Subject subject = SecurityUtils.getSubject();//利用..shiro完成 登录
        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);//执行登录的方法，如果没有异常就说明ok了
            User user = service.checkUser(username, password);
            session.setAttribute("user", user);


//            System.out.println("why is error happen");
            return "admin/index";
        } catch (UnknownAccountException e) {//用户名错误 返回登录界面
            attributes.addFlashAttribute("msg","用户名错误");
//            System.out.println("???");
            return "redirect:/admin";
        } catch (IncorrectCredentialsException e) {//密码错误 返回登录界面
//            System.out.println("!!!");
            attributes.addFlashAttribute("msg","密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/register")
    public String register(){

        return "/admin/register";// 为什么使用重定向不行？
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String avatarUrl,  HttpSession session, RedirectAttributes attributes){

        System.out.println(avatarUrl);
        int flag = service.saveUser(username, password, avatarUrl);
        User user = service.checkUser(username, password);
        session.setAttribute("user", user);
        return "admin/index";



    }






}
