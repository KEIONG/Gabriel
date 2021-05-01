package com.blog.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor



public class Blog implements Serializable {

    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private Date updateTime;

    //这个属性用来在mybatis中进行连接查询的
    private Long typeId;
    private Long userId;

    //获取多个标签的id
    private String tagIds;
    private String description;

    private Type type;

    private User user;

    private List<Tag> tags = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }



    private String tagsToIds(List<Tag> tags){
        Boolean flag = tags.isEmpty();

        if(!flag){
            StringBuffer ids = new StringBuffer();
            Boolean flg = false;
            for(Tag tag: tags){
                if(flg){
                    ids.append(",");

                }else{
                    flg = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }



    }
}
