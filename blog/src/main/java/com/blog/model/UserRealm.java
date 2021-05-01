package com.blog.model;

import com.blog.service.impl.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {


    @Autowired
    UserService service;

    @Override//授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");
        //创建给予授权方法
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //获取当前对象
        Subject subject= SecurityUtils.getSubject();
        //拿到User对象
        User user = (User) subject.getPrincipal();
        //设置当前用户权限
        info.addStringPermission("admin");
        //将权力返回
        return info;
    }
    @Override//认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");
        //强转令牌 获取到从前端封装过来的账号和密码
        UsernamePasswordToken userToken= (UsernamePasswordToken) token;
        //通过传递过来的用户名从数据库查询出对应user
//        User user = userService.queryUserByName(userToken.getUsername());
        User user = service.getUserByUsername(userToken.getUsername());

        if (user == null) {//没有这个人
            return null;
        }
        //密码验证,因为密码事关程序的安全,我们需要交给shiro中SimpleAuthenticationInfo类来做 注意,写在这里我们可以通过subject去取到对应的数据
        System.out.println(user.getPassword());
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }



}
