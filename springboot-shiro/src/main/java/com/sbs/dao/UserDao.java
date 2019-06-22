package com.sbs.dao;

import com.sbs.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    UserDO get(Integer userId);

    List<UserDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserDO user);

    int update(UserDO userDO);

    int remove(Integer userId);

    int batchRemove(Integer[] userIds);
}
