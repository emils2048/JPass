package com.blog.mapper;

import com.blog.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertComment(Comment comment);

    List<Comment> findByTeamId(@Param("teamId") Long teamId);

    Comment findById(@Param("id") Long id);

    void updateComment(@Param("id") Long id, @Param("content") String content);

    void deleteComment(@Param("id") Long id);
}