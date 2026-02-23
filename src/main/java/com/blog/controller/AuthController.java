package com.blog.controller;

import com.blog.domain.User;
import com.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute User user, Model model) {

        if (!userService.registerUser(user)) {
            model.addAttribute("error", "이미 존재하는 아이디/이메일");
            return "auth/join";
        }

        return "redirect:/login";
    }

    // ✅ 여기 수정됨
    @GetMapping("/auth/check-username")
    @ResponseBody
    public ResponseEntity<?> checkUsername(@RequestParam("q") String q) {
        return ResponseEntity.ok(
            java.util.Map.of("available", !userService.isLoginIdTaken(q))
        );
    }

    // ✅ 여기 수정됨
    @GetMapping("/auth/check-email")
    @ResponseBody
    public ResponseEntity<?> checkEmail(@RequestParam("q") String q) {
        return ResponseEntity.ok(
            java.util.Map.of("available", !userService.isEmailTaken(q))
        );
    }
}
