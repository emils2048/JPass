package com.blog.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comment {

    private Long id;
    private Long teamId;
    private Long memberId;   // 저장용 (FK)
    private String nickname; // 조회용 (JOIN 결과)
    private String content;
    private LocalDateTime createdAt;
}