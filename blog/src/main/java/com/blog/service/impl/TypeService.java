package com.blog.service.impl;


import com.blog.mapper.TypeMapper;
import com.blog.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TypeService")
public class TypeService {

    @Autowired
    TypeMapper dao;


    @Transactional
    public int saveType(Type type){
        return dao.saveType(type);
    }

    @Transactional
    public Type getType(Long id){
        return dao.getType(id);
    }

    @Transactional
    public Type getTypeByName(String name){
        return dao.getTypeByName(name);
    }

    @Transactional
    public List<Type> getAllType(){
        return dao.getAllType();
    }

    public List<Type> getBlogType(){
        return dao.getBlogType();
    }


    @Transactional
    public int updateType(Type type){
        return dao.updateType(type);
    }

    public int deleteType(Long id){

        return dao.deleteType(id);
    }







}
