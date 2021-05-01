package com.blog.config;

import com.blog.model.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//申明这是个配置类
public class ShiroConfig {
    //创建ShiroFilterFactoryBean :3
    @Bean//通过Qualifier注解去指定我们安全管理器
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager safetyManager){
        //ShiroFilterFactoryBean对象 它是shiro和spring整合的关键
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //通过bean去设置安全管理器
        bean.setSecurityManager(safetyManager);

        return bean;
    }
    //DefaultWebSecurityManager 该类对应SecurityManager(安全管理器) :2
    @Bean//通过Qualifier注解去指定我们realm对象
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm对象 因为我们需要从realm对象中获取安全数据
        securityManager.setRealm(userRealm());
        return securityManager;
    }
    //创建realm对象 :1
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }


}
