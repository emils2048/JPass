package com.blog.controller;

import com.blog.service.JLeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final JLeagueService jLeagueService;

    // localhost:8077 → index.html
    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("menu", "home");

        // J리그 API 팀
        model.addAttribute("jleagueTeams", jLeagueService.getTeams());

        return "index";
    }
}