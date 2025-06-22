package com.example.employee.mgmt.system.service;

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
}
