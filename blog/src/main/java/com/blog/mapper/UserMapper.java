package com.blog.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.blog.model.User;

@Mapper
@Repository
public interface UserMapper {

    User queryByUsernameAndPassword(@Param("username")String username, @Param("password")String password);

    int saveUser(@Param("username")String username, @Param("password")String password, @Param("avatar") String avatar);

    User queryByUsername(@Param("username")String username);



}
