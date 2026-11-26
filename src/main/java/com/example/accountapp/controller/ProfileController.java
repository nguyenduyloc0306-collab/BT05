package com.example.accountapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.accountapp.service.UserService;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        var user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy user đang đăng nhập"));
        model.addAttribute("user", user);
        return "profile";
    }
}
