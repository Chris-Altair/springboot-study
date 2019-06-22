package com.sbs.domain;

import java.io.Serializable;
import java.util.Date;

public class UserDO implements Serializable {
    private static final Long serialVersionUID = 1L;

    //主键
    private Integer userId;
    //用户名
    private String username;
    //密码
    private String password;
    //状态 0：禁用 1：正常
    private Integer status;
    //是否初次登录 1：是 其他：否
    private Integer islogin;
    //错误登录次数
    private Integer errorLoginCount;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIslogin() {
        return islogin;
    }

    public void setIslogin(Integer islogin) {
        this.islogin = islogin;
    }

    public Integer getErrorLoginCount() {
        return errorLoginCount;
    }

    public void setErrorLoginCount(Integer errorLoginCount) {
        this.errorLoginCount = errorLoginCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
