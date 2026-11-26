package com.example.accountapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {

    private final JdbcTemplate jdbc;

    public RoleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** Tạo role nếu chưa tồn tại (T-SQL). */
    public void ensureRole(String name) {
        jdbc.update("IF NOT EXISTS (SELECT 1 FROM roles WHERE name = ?) INSERT INTO roles(name) VALUES (?)",
                name, name);
    }
}
