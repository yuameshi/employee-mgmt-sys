package com.example.employee.mgmt.system.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.employee.mgmt.system.entity.Employee;

public class EmployeeDao extends BaseDao {

	public Employee findById(Long id) {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		String sql = "SELECT * FROM employee WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, rowMapper, id);
	}

	public List<Employee> findBySex(Employee.EmployeeSex sex) {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		String sql = "SELECT * FROM employee WHERE sex = ?";
		return jdbcTemplate.query(sql, rowMapper, sex.toString());
	}

	public Employee findByDeptId(Long deptId) {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		String sql = "SELECT * FROM employee WHERE dept_id = ?";
		return jdbcTemplate.queryForObject(sql, rowMapper, deptId);
	}

	public Employee findByPhone(String phone) {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		String sql = "SELECT * FROM employee WHERE phone = ? LIMIT 1";
		return jdbcTemplate.queryForObject(sql, rowMapper, phone);
	}

}
