package com.sbs.service;

import com.sbs.domain.UserDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    UserDO get(Integer userId);

    List<UserDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserDO user);

    int update(UserDO userDO);

    int remove(Integer userId);

    int batchRemove(Integer[] userIds);
}
