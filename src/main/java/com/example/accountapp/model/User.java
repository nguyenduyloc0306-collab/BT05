package com.example.accountapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Long id;
    private String username;
    private String password;   // BCrypt hash
    private String fullName;
    private String email;
    private String phone;
    private boolean enabled = true;
    private String roleName;   // "ADMIN", "USER" (không có tiền tố ROLE_)
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /** Dùng cho giao diện (Thymeleaf). */
    public String getCreatedAtDisplay() {
        return createdAt == null ? "" : createdAt.format(FMT);
    }
}
