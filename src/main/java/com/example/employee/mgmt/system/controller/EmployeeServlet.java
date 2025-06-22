package com.example.employee.mgmt.system.controller;

import com.example.employee.mgmt.system.entity.Department;
import com.example.employee.mgmt.system.entity.Employee;
import com.example.employee.mgmt.system.service.DepartmentService;
import com.example.employee.mgmt.system.service.DepartmentServiceImpl;
import com.example.employee.mgmt.system.service.EmployeeService;
import com.example.employee.mgmt.system.service.EmployeeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;

@WebServlet("/employee/*")
public class EmployeeServlet extends BaseServlet {
	EmployeeService employeeService = new EmployeeServiceImpl();
	DepartmentService departmentService = new DepartmentServiceImpl();

	/**
	 * 根据用户名和密码查询数据库中的用户，无返回用户则返回null
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Map<String, String> param = getParam(request);
		String id = param.get("id");
		response.setContentType("application/json;charset=utf8");
		Employee employee = employeeService.findById(Long.parseLong(id));
		if (employee == null) {
			request.setAttribute("msg", "未找到该员工信息");
			request.getRequestDispatcher("/404.jsp").forward(request, response);
			return;
		}
		request.setAttribute("employee", employee);
		// 获取部门信息
		if (employee.getDept() != null) {
			request.setAttribute("department", departmentService.getDepartmentById(employee.getDept()));
			System.out.println(departmentService.getDepartmentById(employee.getDept()));
		}
		request.getRequestDispatcher("/employeeDetail.jsp").forward(request, response);
	}

	/*
	 * 获取全部部门信息
	 */
	public void getAllDepartments(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<Department> depts = departmentService.getAllDepartments();

		response.setContentType("application/json;charset=UTF-8");
		if (depts == null || depts.isEmpty()) {
			response.getWriter().write("[]");
		} else {
			String json = JSON.toJSONString(depts);
			response.getWriter().write(json);
		}
	}

	public void filter(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Map<String, String> param = getParam(request);
		String deptId = param.get("department");
		String name = param.get("name");
		String phone = param.get("phone");
		String gender = param.get("gender");
		// 若没有任何条件则查询全部
		if ((deptId == null || deptId.isEmpty())
				&& (name == null || name.isEmpty())
				&& (phone == null || phone.isEmpty())
				&& (gender == null || gender.isEmpty())) {
			List<Employee> employees = employeeService.getAllEmployees();
			request.setAttribute("employees", employees);
			request.getRequestDispatcher("/employeeList.jsp").forward(request, response);
			return;
		}
		// 若只有手机号则查询手机号
		if (phone != null && !phone.isEmpty()) {
			Employee employee = employeeService.findByPhone(phone);
			if (employee == null) {
				// 没找到去一个特定页面必会404的
				response.sendRedirect("/employee/getById?id=-1");
				return;
			}
			// 找到则直接跳转详情页
			response.sendRedirect("/employee/getById?id=" + employee.getId());
			return;
		}
		// 初始化三个ArrayList
		ArrayList<Long> employeeIdFilteredByGender = new ArrayList<Long>();
		ArrayList<Long> employeeIdFilteredByName = new ArrayList<Long>();
		ArrayList<Long> employeeIdFilteredByDepartment = new ArrayList<Long>();

		// 若有性别则查询性别并转成id array
		if (gender != null && !gender.isEmpty()) {
			List<Employee> employeesFilteredByGender = employeeService.findByGender(gender);
			for (Employee employee : employeesFilteredByGender) {
				employeeIdFilteredByGender.add(employee.getId());
			}
		}

		// 若有名字则查询名字，同上操作
		if (name != null && !name.isEmpty()) {
			List<Employee> employeesFilteredByName = employeeService.findByName(name);
			for (Employee employee : employeesFilteredByName) {
				if (employee.getName() != null && employee.getName().contains(name)) {
					employeeIdFilteredByName.add(employee.getId());
				}
			}
		}

		// 若有部门则查询部门，同上操作
		if (deptId != null && !deptId.isEmpty()) {
			List<Employee> employeesFilteredByDept = employeeService.findByDeptId(Long.parseLong(deptId));
			for (Employee employee : employeesFilteredByDept) {
				employeeIdFilteredByDepartment.add(employee.getId());
			}
		}

		// 求交集（使用 retainAll）
		if (employeeIdFilteredByDepartment.isEmpty() && employeeIdFilteredByGender.isEmpty()
				&& employeeIdFilteredByName.isEmpty()) {
			request.setAttribute("msg", "未找到符合条件的员工信息，请检查查询条件");
			request.getRequestDispatcher("/404.jsp").forward(request, response);
			return;
		}
		ArrayList<Long> employeeIdFiltered;

		// 先获取一个非空的集作为基础集
		if (!employeeIdFilteredByGender.isEmpty()) {
			employeeIdFiltered = new ArrayList<>(employeeIdFilteredByGender);
		} else if (!employeeIdFilteredByDepartment.isEmpty()) {
			employeeIdFiltered = new ArrayList<>(employeeIdFilteredByDepartment);
		} else {
			employeeIdFiltered = new ArrayList<>(employeeIdFilteredByName);
		}
		// 然后与其他集合求交集
		if (!employeeIdFilteredByDepartment.isEmpty()) {
			employeeIdFiltered.retainAll(employeeIdFilteredByDepartment);
		}
		if (!employeeIdFilteredByName.isEmpty()) {
			employeeIdFiltered.retainAll(employeeIdFilteredByName);
		}
		if (!employeeIdFilteredByGender.isEmpty()) {
			employeeIdFiltered.retainAll(employeeIdFilteredByGender);
		}
		System.out.println("employeesFiltered: " + JSON.toJSONString(employeeIdFiltered));

		// 根据过滤后的ID获取员工信息
		List<Employee> filteredEmployees = new ArrayList<>();
		for (Long id : employeeIdFiltered) {
			Employee employee = employeeService.findById(id);
			if (employee != null) {
				filteredEmployees.add(employee);
			}
		}

		// 如果查询到员工则跳转到员工列表页面
		request.setAttribute("employees", filteredEmployees);
		// 设置当前过滤条件
		request.setAttribute("departmentId", deptId);
		request.setAttribute("gender", gender);
		request.setAttribute("name", name);
		// 不用设置手机因为手机号是直接跳转到详情页的
		// 设置部门列表
		List<Department> depts = departmentService.getAllDepartments();
		request.setAttribute("depts", depts);
