package com.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface VisitorMapper {

    void insert(@Param("ip") String ip, @Param("url") String url);

    List<Map<String, Object>> getLast7Days();
}