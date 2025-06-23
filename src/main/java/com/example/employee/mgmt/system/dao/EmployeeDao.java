package com.example.employee.mgmt.system.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.employee.mgmt.system.entity.Employee;

public class EmployeeDao extends BaseDao {

	public Employee findById(Long id) {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		try {
			String sql = "SELECT * FROM employees WHERE id = ? LIMIT 1";
			return jdbcTemplate.queryForObject(sql, rowMapper, id);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Employee> getAllEmployees() {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		String sql = "SELECT * FROM employees";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public Employee findByPhone(String phone) {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		try {
			String sql = "SELECT * FROM employees WHERE phone = ? LIMIT 1";
			return jdbcTemplate.queryForObject(sql, rowMapper, phone);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Employee> findByName(String name) {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		String sql = "SELECT * FROM employees WHERE name like \"" + ("%" + name + "%") + "\"";
		System.out.println(sql);
		return jdbcTemplate.query(sql, rowMapper);
	}

	public List<Employee> findByGender(String sex) {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		String sql = "SELECT * FROM employees WHERE gender = \"" + sex + "\"";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public List<Employee> findByDeptId(Long deptId) {
		BeanPropertyRowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		String sql = "SELECT * FROM employees WHERE dept = ?";
		return jdbcTemplate.query(sql, rowMapper, deptId);
	}

	public void deleteById(Long id) {
		String sql = "DELETE FROM employees WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	public void insert(Employee employee) {
		String sql = "INSERT INTO employees (name, gender, phone, email, dept, hire_date, create_time) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?)";
		// format date to string
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hireDate = sdf.format(employee.getHireDate());
		SimpleDateFormat sdfWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String currentDate = sdfWithTime.format(new java.util.Date());
		jdbcTemplate.update(sql,
				employee.getName(),
				employee.getGender().toString(),
				employee.getPhone(),
				employee.getEmail(),
				employee.getDept(),
				hireDate,
				currentDate);
	}
}
