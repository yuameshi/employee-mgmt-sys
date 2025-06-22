package com.example.employee.mgmt.system.service;

import java.util.List;

import com.example.employee.mgmt.system.dao.EmployeeDao;
import com.example.employee.mgmt.system.entity.Employee;

public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeDao employeeDao;

	public EmployeeServiceImpl() {
		this.employeeDao = new EmployeeDao();
	}

	@Override
	public Employee findById(Long id) {
		return employeeDao.findById(id);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	@Override
	public Employee findByPhone(String phone) {
		return employeeDao.findByPhone(phone);
	}
}
