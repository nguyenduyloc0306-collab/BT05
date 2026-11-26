package com.example.accountapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.accountapp.model.User;
import com.example.accountapp.repository.UserRepository;

/** Spring Security gọi lớp này khi đăng nhập để lấy user từ SQL Server. */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())          // hash BCrypt trong DB
                .roles(u.getRoleName())             // "ADMIN" -> authority "ROLE_ADMIN"
                .disabled(!u.isEnabled())
                .build();
    }
}
