package com.example.employee.mgmt.system.controller;

import com.example.employee.mgmt.system.entity.Employee;
import com.example.employee.mgmt.system.service.EmployeeService;
import com.example.employee.mgmt.system.service.EmployeeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/employee/*")
public class EmployeeServlet extends BaseServlet {
	EmployeeService employeeService = new EmployeeServiceImpl();

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
		request.getRequestDispatcher("/employeeDetail.jsp").forward(request, response);
	}
}
