package com.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.blog.service.VisitorService;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final VisitorService visitorService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("stats", visitorService.getLast7DaysStats());
        return "admin/dashboard";
    }
}