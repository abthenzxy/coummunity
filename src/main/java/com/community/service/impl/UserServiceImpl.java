package com.community.service.impl;

import com.community.dao.UserDao;
import com.community.pojo.User;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author young
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public void addUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public String findAccountId(String name) {
        return userDao.seekAccountId(name);
    }

    @Override
    public User findUserById(String token) {
        return userDao.seekUserByToken(token);
    }


}
