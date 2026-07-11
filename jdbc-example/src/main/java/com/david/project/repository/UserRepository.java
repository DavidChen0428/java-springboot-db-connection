package com.david.project.repository;

import com.david.project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // INSERT
    public int save(User user) {
        String sql = """
                INSERT INTO USERS (NAME, GENDER, PHONE_NUMBER, EMAIL, ADDRESS)
                VALUES (?, ?, ?, ?, ?)
                """;
        return jdbcTemplate.update(sql, user.getName(), user.getGender(), user.getPhoneNumber(), user.getEmail(), user.getAddress());
    }

    // SELECT
    public List<User> findAll() {
        String sql = """
                SELECT * FROM USERS
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(rs.getInt("ID"), rs.getString("NAME"), rs.getString("GENDER"), rs.getString("PHONE_NUMBER"), rs.getString("EMAIL"), rs.getString("ADDRESS")));
    }

    public User findById(Integer id) {
        String sql = """
                SELECT * FROM USERS
                WHERE ID = ?
                """;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(rs.getInt("ID"), rs.getString("NAME"), rs.getString("GENDER"), rs.getString("PHONE_NUMBER"), rs.getString("EMAIL"), rs.getString("ADDRESS")),id);
    }

    // UPDATE
    public int update(User user) {
        String sql = """
                UPDATE USERS
                SET NAME = ?,
                    GENDER = ?,
                    PHONE_NUMBER = ?,
                    EMAIL = ?,
                    ADDRESS = ?
                WHERE
                    ID = ?
                """;
        return jdbcTemplate.update(sql, user.getName(), user.getGender(), user.getPhoneNumber(), user.getEmail(), user.getAddress(), user.getId());
    }

    // DELETE
    public int deleteById(Integer id) {
        String sql = """
                DELETE FROM USERS
                WHERE ID = ?
                """;
        return jdbcTemplate.update(sql, id);
    }
}
