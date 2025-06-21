package com.example.employee.mgmt.system.filter;

import com.example.employee.mgmt.system.entity.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 登录了就直接放行
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser != null) {
			filterChain.doFilter(request, response);
			return;
		}

		String requestURI = request.getRequestURI();

		String[] allowedURIs = {
				"/login.jsp",
				"/index.jsp",
				"/user/login" };

		for (String uri : allowedURIs) {
			if (requestURI.startsWith(uri)) {
				// 这些都是不受限资源，直接放行
				filterChain.doFilter(request, response);
				return;
			}
		}

		// 受限资源，而且还没有登录则跳转登录
		response.sendRedirect("/login.jsp");

	}

	@Override
	public void destroy() {
	}
}
