package com.blog.service.impl;


import com.blog.mapper.TagMapper;
import com.blog.model.Blog;
import com.blog.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {


    @Autowired
    TagMapper dao;

    public int saveTag(Tag tag){
        return dao.saveTag(tag);
    }

    public Tag getTag(Long id){
        return dao.getTag(id);
    }

    public Tag getTagByName(String name){
        return dao.getTagByName(name);
    }

    public List<Tag> getAllTag(){
        return dao.getAllTag();
    }

    public List<Tag> getBlogTag(){
        return dao.getBlogTag();
    }

    public List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idArray = ids.split(",");
            for(int i = 0; i < idArray.length; i++){
                list.add(new Long(idArray[i]));
            }

        }
        return list;
    }

    public int updateTag(Tag tag){
        return dao.updateTag(tag);
    }

    public int deleteTag(Long id){
        return dao.deleteTag(id);
    }

    public List<Tag> getTagByString(String text){
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = convertToList(text);
        for(Long tmp: longs){
            tags.add(dao.getTag(tmp));
        }
        return tags;
    }



}
