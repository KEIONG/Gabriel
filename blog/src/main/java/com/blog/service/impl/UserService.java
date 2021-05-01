package com.blog.service.impl;


import com.blog.mapper.UserMapper;
import com.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service("UserService")
@EnableCaching
public class UserService {


    @Autowired
    UserMapper dao;


//    @Cacheable(value = {"checkUser2"}, key = "#username")
    public User checkUser(String username, String password){
        User user = dao.queryByUsernameAndPassword(username, password);
        return user;

    }
    public int saveUser(String username, String password, String avatar){
        int flag = dao.saveUser(username, password, avatar);
        return flag;
    }

    public User getUserByUsername(String username){
        User user = dao.queryByUsername(username);
        return user;
    }


}
