package com.blog.service;

import com.blog.domain.Comment;
import com.blog.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    // 댓글 작성
    public void writeComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    // 팀별 댓글 조회
    public List<Comment> getCommentsByTeamId(Long teamId) {
        return commentMapper.findByTeamId(teamId);
    }

    // 단건 댓글 조회
    public Comment getCommentById(Long id) {
        return commentMapper.findById(id);
    }

    // 댓글 수정
    public void modifyComment(Comment comment) {
        commentMapper.updateComment(comment.getId(), comment.getContent());
    }

    // 댓글 삭제
    public void removeComment(Long id) {
        commentMapper.deleteComment(id);
    }
}