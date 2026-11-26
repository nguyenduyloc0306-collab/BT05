package com.example.accountapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.accountapp.dto.RegisterForm;
import com.example.accountapp.exception.DuplicateAccountException;
import com.example.accountapp.model.User;
import com.example.accountapp.repository.UserRepository;

@Service
public class UserService {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterForm form) {
        String username = form.getUsername().trim();
        String email = form.getEmail().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) {
            throw new DuplicateAccountException("username", "Tên đăng nhập đã tồn tại");
        }
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateAccountException("email", "Email đã được sử dụng");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFullName(form.getFullName().trim());
        user.setEmail(email);
        String phone = form.getPhone() == null ? "" : form.getPhone().trim();
        user.setPhone(phone.isEmpty() ? null : phone);
        user.setEnabled(true);
        user.setRoleName(ROLE_USER);   // đăng ký công khai luôn là USER

        try {
            userRepository.save(user);
        } catch (DuplicateKeyException ex) {   // 2 request đăng ký trùng nhau cùng lúc
            throw new DuplicateAccountException("username", "Tên đăng nhập hoặc email đã tồn tại");
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
