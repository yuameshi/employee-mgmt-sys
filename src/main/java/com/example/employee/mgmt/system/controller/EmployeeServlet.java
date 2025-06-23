package com.example.employee.mgmt.system.controller;

import com.example.employee.mgmt.system.entity.Department;
import com.example.employee.mgmt.system.entity.Employee;
import com.example.employee.mgmt.system.entity.User;
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
			request.setAttribute("title", "找不到对象");
			request.setAttribute("msg", "未找到该员工信息");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
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
		// 设置部门列表
		List<Department> depts = departmentService.getAllDepartments();
		request.setAttribute("depts", depts);
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
			// 设置isEmpty为false表示有员工
			request.setAttribute("isEmpty", false);
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
		// 初始化三个ArrayList为null（有对应查询条件时才会被赋值）
		// 用于存储过滤后的员工ID
		ArrayList<Long> employeeIdFilteredByGender = null;
		ArrayList<Long> employeeIdFilteredByName = null;
		ArrayList<Long> employeeIdFilteredByDepartment = null;

		// 若有性别筛选则查询性别并转成id array
		if (gender != null && !gender.isEmpty()) {
			List<Employee> employeesFilteredByGender = employeeService.findByGender(gender);
			if (employeesFilteredByGender == null || employeesFilteredByGender.isEmpty()) {
				// 如果没有找到符合性别的员工，则直接返回空结果（后面再怎么交集也是空）
				// 不再赋值筛选器选项（重置筛选器）
				request.setAttribute("isEmpty", true);
				request.getRequestDispatcher("/employeeList.jsp").forward(request, response);
				return;
			}
			employeeIdFilteredByGender = new ArrayList<Long>();
			for (Employee employee : employeesFilteredByGender) {
				employeeIdFilteredByGender.add(employee.getId());
			}
		}

		// 若有名字则查询名字，同上操作
		if (name != null && !name.isEmpty()) {
			List<Employee> employeesFilteredByName = employeeService.findByName(name);
			if (employeesFilteredByName == null || employeesFilteredByName.isEmpty()) {
				// 如果没有找到符合名字的员工，则直接返回空结果（同上）
				request.setAttribute("isEmpty", true);
				request.getRequestDispatcher("/employeeList.jsp").forward(request, response);
				return;
			}
			employeeIdFilteredByName = new ArrayList<Long>();
			for (Employee employee : employeesFilteredByName) {
				if (employee.getName() != null && employee.getName().contains(name)) {
					employeeIdFilteredByName.add(employee.getId());
				}
			}
		}

		// 若有部门则查询部门，同上操作
		if (deptId != null && !deptId.isEmpty()) {
			List<Employee> employeesFilteredByDept = employeeService.findByDeptId(Long.parseLong(deptId));
			if (employeesFilteredByDept == null || employeesFilteredByDept.isEmpty()) {
				// 如果没有找到符合部门的员工，则直接返回空结果（同上）
				request.setAttribute("isEmpty", true);
				request.getRequestDispatcher("/employeeList.jsp").forward(request, response);
				return;
			}
			employeeIdFilteredByDepartment = new ArrayList<Long>();
			for (Employee employee : employeesFilteredByDept) {
				employeeIdFilteredByDepartment.add(employee.getId());
			}
		}

		ArrayList<Long> employeeIdFiltered = null;

		// 求交集（使用 retainAll）
		// 先获取一个非空的集作为基础集
		// 因为其中有筛选不出来的 或者 没有此条件的情况下就是 null
		// 因此不判断isEmpty
		if (employeeIdFilteredByGender != null) {
			employeeIdFiltered = new ArrayList<>(employeeIdFilteredByGender);
		} else if (employeeIdFilteredByDepartment != null) {
			employeeIdFiltered = new ArrayList<>(employeeIdFilteredByDepartment);
		} else {
			employeeIdFiltered = new ArrayList<>(employeeIdFilteredByName);
		}
		// 然后与其他集合求交集
		// 只有有该筛选条件（不为null）才会进行交集操作
		if (employeeIdFilteredByDepartment != null) {
			employeeIdFiltered.retainAll(employeeIdFilteredByDepartment);
		}
		if (employeeIdFilteredByName != null) {
			employeeIdFiltered.retainAll(employeeIdFilteredByName);
		}
		if (employeeIdFilteredByGender != null) {
			employeeIdFiltered.retainAll(employeeIdFilteredByGender);
		}

		if (employeeIdFiltered.isEmpty()) {
			// 如果没有找到符合条件的员工，则返回空结果
			request.setAttribute("isEmpty", true);
			request.getRequestDispatcher("/employeeList.jsp").forward(request, response);
			return;
		}

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
		// 设置isEmpty为false表示有员工，不用ArrayList.isEmpty()判断
		request.setAttribute("isEmpty", false);
		// 设置当前过滤条件
		request.setAttribute("departmentId", deptId);
		request.setAttribute("gender", gender);
		request.setAttribute("name", name);
		// 不用设置手机因为手机号是直接跳转到详情页的
		request.getRequestDispatcher("/employeeList.jsp").forward(request, response);
	}

	public void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 获取当前用户等级
		if (loginUser.getRole() != User.UserRole.ADMIN) {
			// 如果不是管理员则跳转到403页面
			request.setAttribute("title", "访问拒绝");
			request.setAttribute("msg", "没有权限执行此操作");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		Map<String, String> param = getParam(request);
		String id = param.get("id");
		if (id == null || id.isEmpty()) {
			// 如果没有id则跳转到404页面
			request.setAttribute("title", "找不到对象");
			request.setAttribute("msg", "未找到该员工信息");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		} else {
			// 删除员工
			try {
				employeeService.delete(Long.parseLong(id));
				// 删除成功后跳转到员工列表页面
				response.sendRedirect("/employee/filter");
			} catch (Exception e) {
				// 如果删除失败则跳转到404页面
				request.setAttribute("title", "找不到对象");
				request.setAttribute("msg", "删除员工失败或未找到该员工信息，请稍后再试");
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}
	}

	public void addEmployee(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 获取当前用户等级
		if (loginUser.getRole() != User.UserRole.ADMIN) {
			// 如果不是管理员则跳转到403页面
			request.setAttribute("title", "访问拒绝");
			request.setAttribute("msg", "没有权限执行此操作");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		// 获取所有部门信息
		List<Department> depts = departmentService.getAllDepartments();
		request.setAttribute("depts", depts);
		request.getRequestDispatcher("/addEmployee.jsp").forward(request, response);
	}

	public void addEmployeeSubmit(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 获取当前用户等级
		if (loginUser.getRole() != User.UserRole.ADMIN) {
			// 如果不是管理员则跳转到403页面
			request.setAttribute("title", "访问拒绝");
			request.setAttribute("msg", "没有权限执行此操作");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		Map<String, String> param = getParam(request);
		String name = param.get("name");
		String phone = param.get("phone");
		String gender = param.get("gender");
		String email = param.get("email");
		String deptId = param.get("dept");
		String hireDate = param.get("hireDate");
		if ((name == null || name.isEmpty())
				|| (phone == null || phone.isEmpty())
				// 还要判断是否是合规的性别
				|| (gender == null || gender.isEmpty())
				|| (email == null || email.isEmpty())
				|| (deptId == null || deptId.isEmpty())
				|| (hireDate == null || hireDate.isEmpty())) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.setAttribute("title", "请求错误");
			request.setAttribute("msg", "请填写完整的员工信息");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		} else {
			// 创建员工对象
			Employee employee = new Employee();
			employee.setName(name);
			employee.setPhone(phone);
			if (gender.equals("MALE")) {
				employee.setGender(Employee.EmployeeSex.MALE);
			} else if (gender.equals("FEMALE")) {
				employee.setGender(Employee.EmployeeSex.FEMALE);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				request.setAttribute("title", "请求错误");
				request.setAttribute("msg", "性别格式不正确");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			employee.setEmail(email);
			employee.setDept(Long.parseLong(deptId));
			// Convert hireDate String to java.util.Date
			try {
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				java.util.Date parsedHireDate = sdf.parse(hireDate);
				employee.setHireDate(parsedHireDate);
			} catch (java.text.ParseException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				request.setAttribute("title", "请求错误");
				request.setAttribute("msg", "入职日期格式不正确");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			// 插入员工信息
			try {
				employeeService.addEmployee(employee);
				// 如果插入成功则查询员工ID
				Employee insertedEmployee = employeeService.findByPhone(phone);
				if (insertedEmployee == null) {
					// 如果没有找到插入的员工则跳转到404页面
					request.setAttribute("title", "服务器错误");
					request.setAttribute("msg", "添加员工失败，请稍后再试");
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					request.getRequestDispatcher("/error.jsp").forward(request, response);
					return;
				}
				// 插入成功后跳转到员工详情页面
				response.sendRedirect("/employee/getById?id=" + insertedEmployee.getId());
			} catch (Exception e) {
				e.printStackTrace();
				// 如果插入失败则跳转到404页面
				request.setAttribute("title", "服务器错误");
				request.setAttribute("msg", "添加员工失败，请稍后再试");
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}
	}
}
