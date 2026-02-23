package com.blog.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {

    private Long id;
    private String loginId;
    private String password;
    private String email;
    private String nickname;
    private String role;
    private boolean status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
