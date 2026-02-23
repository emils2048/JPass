package com.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.blog.mapper.VisitorMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorMapper visitorMapper;

    public void logVisit(String ip, String url) {
        visitorMapper.insert(ip, url);
    }

    public List<Map<String, Object>> getLast7DaysStats() {
        return visitorMapper.getLast7Days();
    }
}