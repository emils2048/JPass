package com.blog.controller;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.service.JLeagueService;
import com.blog.service.CommentService; // 🔹 추가

@Controller
@RequestMapping("/jleague")
@RequiredArgsConstructor
public class JLeagueController {

    private final JLeagueService jleagueService;
    private final CommentService commentService; // 🔹 추가

    @GetMapping
    public String main() {
        return "jleague/jleagueMain";
    }

    @GetMapping("/matchday")
    public String matchday(Model model) {
        model.addAttribute("matchday", jleagueService.getMatch2024());
        return "jleague/matchday";
    }

    @GetMapping("/live")
    public String live(Model model) {
        model.addAttribute("liveMatches", jleagueService.getLiveMatches());
        return "jleague/live";
    }

    @GetMapping("/standings")
    public String standings(Model model) {
        model.addAttribute("standings", jleagueService.getStandings());
        return "jleague/standings";
    }

    @GetMapping("/teams")
    public String teams(Model model) {
        model.addAttribute("teams", jleagueService.getTeams());
        return "jleague/teams";
    }

    @GetMapping("/teams/{id}")
    public String teamDetail(@PathVariable("id") Long id, Model model) {
        Map<String,Object> team = jleagueService.getTeamById(id);
        model.addAttribute("team", team);
        model.addAttribute("teamId", id);

        // 🔹 추가: 팀별 댓글 모델에 담기
        model.addAttribute("comments", commentService.getCommentsByTeamId(id));

        return "teams/detail";
    }
}