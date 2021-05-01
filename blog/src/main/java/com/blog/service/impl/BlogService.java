package com.blog.service.impl;


import com.blog.mapper.BlogMapper;
import com.blog.model.Blog;
import com.blog.model.BlogAndTag;
import com.blog.model.Tag;
import com.blog.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.blog.exception.NotFoundException;

import java.util.*;


@Service("BlogService")
public class BlogService  {

    @Autowired
    BlogMapper dao;


//    @Cacheable(value = {"getBlog"}, key = "#id")
    public Blog getBlog(Long id){
        return dao.getBlog(id);
    }

//    @Cacheable(value = {"detailedBlog"}, key = "#id")
    public Blog getDetailedBlog(Long id) {
        Blog blog = dao.getDetailedBlog(id);
        if(blog == null){
            throw new NotFoundException("Blog is not Found!");
        }

        blog.setContent(MarkdownUtils.markdownToHtml(blog.getContent()));
        return blog;

    }

//    @Cacheable(value = {"allBlog"})
    public List<Blog> getAllBlog(){
        return dao.getAllBlog();
    }

//
//    @Cacheable(value = {"blogByTypeId"}, key = "#typeId")
    public List<Blog> getByTypeId(Long typeId){
//        System.out.println("test test!!!");
        return dao.getByTypeId(typeId);
    }



//    @Cacheable(value = {"blogByTagId"}, key = "#tagId")
    public List<Blog> getByTagId(Long tagId){
        return dao.getByTagId(tagId);
    }

//    @Cacheable(value = {"blogByUserId"}, key = "#userId")
    public List<Blog> getByUserId(Long userId) {
        return dao.getByUserId(userId);
    }

//    @Cacheable(value = {"indexBlog"})
    public List<Blog> getIndexBlog(){
        return dao.getIndexBlog();
    }

//    @Cacheable(value = {"allRecommendBlog"})
    public List<Blog> getAllRecommendBlog(){
        return dao.getAllRecommendBlog();
    }

    public List<Blog> getSearchBlog(String query){
        return dao.getSearchBlog(query);
    }

    public Map<String, List<Blog>> archiveBlog(){
        List<String> years = dao.findGroupYear();
        Set<String> set = new HashSet<>(years);
        Map<String, List<Blog>> map = new HashMap<>();
        for(String year : set){
            map.put(year, dao.findByYear(year));
        }
        return map;

    }

    public int countBlog(){
        return dao.getAllBlog().size();
    }

    public List<Blog> searchAllBlog(Blog blog){
        return dao.searchAllBlog(blog);
    }

    public int savaBlog(Blog blog){
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //保存博客
        dao.saveBlog(blog);
        //保存博客后才能获取自增的id
        Long id = blog.getId();
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            //新增时无法获取自增的id,在mybatis里修改
            blogAndTag = new BlogAndTag(tag.getId(), id);
            dao.saveBlogAndTag(blogAndTag);
        }
        return 1;

    }

    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            blogAndTag = new BlogAndTag(tag.getId(), blog.getId());
            dao.saveBlogAndTag(blogAndTag);
        }
        return dao.updateBlog(blog);
    }

    public int deleteBlog(Long id){
        return dao.deleteBlog(id);
    }









    


}
