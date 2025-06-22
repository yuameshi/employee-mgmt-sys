package com.example.employee.mgmt.system.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.employee.mgmt.system.entity.Department;

public class DepartmentDao extends BaseDao {
	public Department getDepartmentById(Long deptId) {
		BeanPropertyRowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
		String sql = "SELECT * FROM departments WHERE dept_id = ? LIMIT 1";
		try {
			return jdbcTemplate.queryForObject(sql, rowMapper, deptId);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Department> getAllDepartments() {
		BeanPropertyRowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
		String sql = "SELECT * FROM departments";
		return jdbcTemplate.query(sql, rowMapper);
	}
}
