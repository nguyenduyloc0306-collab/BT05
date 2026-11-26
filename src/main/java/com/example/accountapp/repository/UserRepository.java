package com.example.accountapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.accountapp.model.User;

@Repository
public class UserRepository {

    private static final String BASE_SELECT =
            "SELECT u.id, u.username, u.password, u.full_name, u.email, u.phone, "
          + "u.enabled, u.created_at, r.name AS role_name "
          + "FROM users u JOIN roles r ON u.role_id = r.id ";

    private static final RowMapper<User> MAPPER = UserRepository::mapRow;

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setEnabled(rs.getBoolean("enabled"));
        u.setRoleName(rs.getString("role_name"));
        Timestamp ts = rs.getTimestamp("created_at");
        u.setCreatedAt(ts == null ? null : ts.toLocalDateTime());
        return u;
    }

    public Optional<User> findByUsername(String username) {
        List<User> list = jdbc.query(BASE_SELECT + "WHERE u.username = ?", MAPPER, username);
        return list.stream().findFirst();
    }

    public List<User> findAll() {
        return jdbc.query(BASE_SELECT + "ORDER BY u.id", MAPPER);
    }

    public boolean existsByUsername(String username) {
        Integer c = jdbc.queryForObject("SELECT COUNT(*) FROM users WHERE username = ?", Integer.class, username);
        return c != null && c > 0;
    }

    public boolean existsByEmail(String email) {
        Integer c = jdbc.queryForObject("SELECT COUNT(*) FROM users WHERE email = ?", Integer.class, email);
        return c != null && c > 0;
    }

    /** Lưu user mới; role_id lấy từ bảng roles theo user.getRoleName(). */
    public void save(User user) {
        jdbc.update(
            "INSERT INTO users (username, password, full_name, email, phone, enabled, role_id) "
          + "VALUES (?, ?, ?, ?, ?, ?, (SELECT id FROM roles WHERE name = ?))",
            user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail(),
            user.getPhone(), user.isEnabled(), user.getRoleName());
    }
}
