package com.community.dao;

import com.community.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author young
 */
@Repository
public interface UserDao {
    /**
     * 插入用户数据
     * @param user
     */
    void insertUser(User user);

    String seekAccountId(String username);

    User seekUserByToken(String token);
}
