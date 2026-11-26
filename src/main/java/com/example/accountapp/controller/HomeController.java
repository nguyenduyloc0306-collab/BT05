package com.example.accountapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";                 // -> /WEB-INF/views/index.jsp
    }

    /** Spring Security chuyển (forward) về đây khi user không đủ quyền. */
    @RequestMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied() {
        return "error/403";             // -> /WEB-INF/views/error/403.jsp
    }
}
