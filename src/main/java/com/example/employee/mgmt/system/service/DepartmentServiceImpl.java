package com.example.employee.mgmt.system.service;

import com.example.employee.mgmt.system.dao.DepartmentDao;
import com.example.employee.mgmt.system.entity.Department;

public class DepartmentServiceImpl implements DepartmentService {
	DepartmentDao departmentDao;

	public DepartmentServiceImpl() {
		this.departmentDao = new DepartmentDao();
	}

	@Override
	public Department getDepartmentById(Long id) {
		return departmentDao.getDepartmentById(id);
	}
}
