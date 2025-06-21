package com.example.employee.mgmt.system.controller;

import com.example.employee.mgmt.system.entity.User;
import com.example.employee.mgmt.system.service.UserService;
import com.example.employee.mgmt.system.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
	UserService userService = new UserServiceImpl();

	/**
	 * 根据用户名和密码查询数据库中的用户，无返回用户则进行提示
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Map<String, String> param = getParam(request);

		String username = param.get("username");
		String password = param.get("password");
		User user = userService.login(username, password);
		response.setContentType("application/json;charset=utf8");
		if (user != null) {
			// 写入session
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			// 更新最后登录时间
			userService.updateLastLoginTime(user.getUserId());
			// 进入员工列表页
			response.sendRedirect("/employeeList.jsp");
			return;
		}
		// 账号或密码错误
		request.setAttribute("msg", "账号或密码错误");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
}
