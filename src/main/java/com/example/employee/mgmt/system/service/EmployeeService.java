package com.example.employee.mgmt.system.service;

import java.util.List;

import com.example.employee.mgmt.system.entity.Employee;

public interface EmployeeService {
	Employee findById(Long id);

	List<Employee> getAllEmployees();

	Employee findByPhone(String phone);
}
