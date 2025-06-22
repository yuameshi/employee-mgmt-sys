package com.example.employee.mgmt.system.service;

import java.util.List;

import com.example.employee.mgmt.system.entity.Employee;

public interface EmployeeService {
	Employee findById(Long id);

	List<Employee> getAllEmployees();

	Employee findByPhone(String phone);

	List<Employee> findByName(String name);

	List<Employee> findByGender(String sex);

	List<Employee> findByDeptId(Long deptId);
}
