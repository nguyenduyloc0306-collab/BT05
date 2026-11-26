package com.example.accountapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.accountapp.dto.RegisterForm;
import com.example.accountapp.exception.DuplicateAccountException;
import com.example.accountapp.service.UserService;

import jakarta.validation.Valid;

/**
 * Đăng ký: xử lý ở đây.
 * Đăng nhập (POST /login) và đăng xuất (POST /logout): do Spring Security xử lý (xem SecurityConfig).
 */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm form,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {

        if (form.getPassword() != null && form.getConfirmPassword() != null
                && !form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "mismatch", "Mật khẩu nhập lại không khớp");
        }
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.register(form);
        } catch (DuplicateAccountException ex) {
            result.rejectValue(ex.getField(), "duplicate", ex.getMessage());
            return "register";
        }

        redirectAttributes.addFlashAttribute("registered", true);
        return "redirect:/login";
    }
}
