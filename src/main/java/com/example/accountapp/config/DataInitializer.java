package com.example.accountapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.accountapp.model.User;
import com.example.accountapp.repository.RoleRepository;
import com.example.accountapp.repository.UserRepository;
import com.example.accountapp.service.UserService;

/** Đảm bảo có role ADMIN/USER và 1 tài khoản admin mặc định khi ứng dụng khởi động. */
@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}") private String adminUsername;
    @Value("${app.admin.password}") private String adminPassword;
    @Value("${app.admin.full-name}") private String adminFullName;
    @Value("${app.admin.email}") private String adminEmail;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        roleRepository.ensureRole(UserService.ROLE_ADMIN);
        roleRepository.ensureRole(UserService.ROLE_USER);

        if (!userRepository.existsByUsername(adminUsername)) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setFullName(adminFullName);
            admin.setEmail(adminEmail);
            admin.setEnabled(true);
            admin.setRoleName(UserService.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
}
