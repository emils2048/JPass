package com.blog.service;

import com.blog.domain.User;
import com.blog.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public boolean registerUser(User user) {

        // 중복 체크
        if (isLoginIdTaken(user.getLoginId()) ||
            isEmailTaken(user.getEmail())) {
            return false;
        }

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 기본값 설정
        user.setRole("ROLE_USER");
        user.setStatus(true);

        userMapper.insertUser(user);

        return true;
    }

    public boolean isLoginIdTaken(String loginId) {
        return userMapper.existsByLoginId(loginId) > 0;
    }

    public boolean isEmailTaken(String email) {
        return userMapper.existsByEmail(email) > 0;
    }
}
