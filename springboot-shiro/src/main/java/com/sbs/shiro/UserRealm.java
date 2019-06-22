package com.sbs.shiro;

import com.sbs.config.ApplicationContextRegister;
import com.sbs.dao.UserDao;
import com.sbs.domain.UserDO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class UserRealm extends AuthorizingRealm {
//    @Autowired
//    private UserDao userMapper;
    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Integer userId = ShiroUtils.getUserId();
        UserDao userMapper = ApplicationContextRegister.getBean(UserDao.class);
        UserDO user = userMapper.get(userId);
        Set<String> perms = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(user.getUserId() == 1){
            perms.add("sys:test");
        }
//        info.setRoles();
        info.setStringPermissions(perms);
        return info;
    }
    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param token 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Map<String, Object> map = new HashMap<>(16);
        String username = (String) token.getPrincipal();//获取令牌的身份
        String password = new String((char[]) token.getCredentials());//获取令牌的密码
        map.put("username", username);

        UserDao userMapper = ApplicationContextRegister.getBean(UserDao.class);
        List<UserDO> userDOList = userMapper.list(map);
        if(userDOList.size() <= 0){
            throw new UnknownAccountException("用户不存在");
        }
        //查询账户信息
        UserDO user = userDOList.get(0);
        // 账号锁定
        if (user.getStatus() == 0) {
            Duration duration = Duration.between(user.getUpdateTime().toInstant(), Instant.now());
            if(duration.toMinutes()<10){
                throw new LockedAccountException("账号已被锁定,请"+(10-duration.toMinutes())+"分钟后重试。");
            }else {
                user.setStatus(1);
                user.setErrorLoginCount(0);
                userMapper.update(user);
            }
        }
        if(!password.equals(user.getPassword())){
            if(null != user.getErrorLoginCount()){
                Duration duration = Duration.between(user.getUpdateTime().toInstant(), Instant.now());
                if(duration.toMinutes()>10){
                    user.setErrorLoginCount(1);
                }else{
                    user.setErrorLoginCount(user.getErrorLoginCount()+1);
                }
                if(user.getErrorLoginCount()>4){
                    user.setStatus(0);
                    user.setUpdateTime(new Date());
                    userMapper.update(user);
                    throw new LockedAccountException("已到达最大尝试次数，请10分钟后尝试。");
                }
                user.setUpdateTime(new Date());
                userMapper.update(user);
                throw new LockedAccountException("账号或密码不正确，已尝试登录"+(user.getErrorLoginCount())+"次，剩余"+(5-user.getErrorLoginCount())+"次机会。");
            }else{
                user.setUpdateTime(new Date());
                user.setErrorLoginCount(1);
                userMapper.update(user);
                throw new LockedAccountException("账号或密码不正确，已尝试登录1次，剩余4次机会。");
            }
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
