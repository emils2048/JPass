package com.blog.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.blog.config.CustomUserDetails;
import com.blog.domain.Comment;
import com.blog.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 팀별 댓글 조회
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("teamId") Long teamId) {
        return ResponseEntity.ok(commentService.getCommentsByTeamId(teamId));
    }

    // 댓글 작성
    @PostMapping("/team/{teamId}")
    public ResponseEntity<Comment> addComment(
            @PathVariable("teamId") Long teamId,
            @RequestBody Map<String, String> body,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String content = body.get("content");
        if (content == null || content.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Comment comment = new Comment();
        comment.setTeamId(teamId);
        comment.setMemberId(user.getId());
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        // DB insert 후 자동 생성 id 세팅
        commentService.writeComment(comment);

        // 화면 표시용 닉네임
        comment.setNickname(user.getNickname());

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody Map<String, String> body,
            Authentication authentication) {

        String content = body.get("content");
        if (content == null || content.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Comment existing = commentService.getCommentById(commentId);

        if (existing == null) return ResponseEntity.notFound().build();

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!existing.getMemberId().equals(user.getId()) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        existing.setContent(content);
        commentService.modifyComment(existing);

        return ResponseEntity.ok(existing);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("commentId") Long commentId,
            Authentication authentication) {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Comment existing = commentService.getCommentById(commentId);

        if (existing == null) return ResponseEntity.notFound().build();

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!existing.getMemberId().equals(user.getId()) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        commentService.removeComment(commentId);
        return ResponseEntity.noContent().build();
    }
}