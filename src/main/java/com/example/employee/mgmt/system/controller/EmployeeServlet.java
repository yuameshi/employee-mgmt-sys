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
		// 这个总得写的
		List<Department> depts = departmentService.getAllDepartments();
		request.setAttribute("depts", depts);

		Map<String, String> param = getParam(request);
		String deptId = param.get("deptId");
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
		List<Employee> employees = employeeService.getAllEmployees();
		// 如果查询到员工则跳转到员工列表页面
		request.setAttribute("employees", employees);
		request.getRequestDispatcher("/employeeList.jsp").forward(request, response);
	}
}
