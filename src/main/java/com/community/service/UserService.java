package com.community.service;

import com.community.pojo.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author young
 */
@Transactional(rollbackFor = Exception.class)
public interface UserService {
     void addUser(User user);

    /**根据姓名查找ACCOUNTID来判断是否新用户
     * @param username
     * @return
     */
    String findAccountId(String name);

    User findUserById(String token);

}
