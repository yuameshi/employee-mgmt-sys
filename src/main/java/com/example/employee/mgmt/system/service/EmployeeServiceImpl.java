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

	@Override
	public List<Employee> findByName(String name) {
		return employeeDao.findByName(name);
	}

	@Override
	public List<Employee> findByGender(String sex) {
		return employeeDao.findByGender(sex);
	}

	@Override
	public List<Employee> findByDeptId(Long deptId) {
		return employeeDao.findByDeptId(deptId);
	}

	@Override
	public void delete(Long id) {
		employeeDao.deleteById(id);
	}

	@Override
	public void addEmployee(Employee employee) {
		employeeDao.insert(employee);
	}
}
