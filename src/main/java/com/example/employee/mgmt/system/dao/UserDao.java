package com.example.employee.mgmt.system.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import com.example.employee.mgmt.system.entity.User;

public class UserDao extends BaseDao {
	public User getByCredentials(String identity, String password) {
		BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		String sql = "SELECT * FROM user WHERE (username = ? OR user_id = ?) AND password = ? LIMIT 1";
		try {
			return jdbcTemplate.queryForObject(sql, rowMapper, identity, identity, password);
		} catch (Exception e) {
			return null;
		}
	}

	public void updateLastLoginTime(Long userId) {
		String sql = "UPDATE user SET last_login_time = NOW() WHERE user_id = ?";
		jdbcTemplate.update(sql, userId);
	}
}
