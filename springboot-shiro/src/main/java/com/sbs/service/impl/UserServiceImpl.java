package com.sbs.service.impl;

import com.sbs.dao.UserDao;
import com.sbs.domain.UserDO;
import com.sbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDO get(Integer userId) {
        return userDao.get(userId);
    }

    @Override
    public List<UserDO> list(Map<String, Object> map) {
        return userDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userDao.count(map);
    }

    @Override
    public int save(UserDO user) {
        return userDao.save(user);
    }

    @Override
    public int update(UserDO user) {
        return userDao.update(user);
    }

    @Override
    public int remove(Integer userId) {
        return userDao.remove(userId);
    }

    @Override
    public int batchRemove(Integer[] userIds) {
        return userDao.batchRemove(userIds);
    }
}
