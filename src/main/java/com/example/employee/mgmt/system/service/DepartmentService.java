package com.example.employee.mgmt.system.service;

import java.util.List;

import com.example.employee.mgmt.system.entity.Department;

public interface DepartmentService {
	Department getDepartmentById(Long id);

	List<Department> getAllDepartments();
}
