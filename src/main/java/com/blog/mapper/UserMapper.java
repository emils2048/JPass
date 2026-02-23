package com.blog.mapper;

import com.blog.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findByLoginId(String loginId);

    void insertUser(User user);

    int existsByLoginId(String loginId);

    int existsByEmail(String email);
}
