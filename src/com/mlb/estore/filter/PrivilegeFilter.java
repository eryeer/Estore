package com.mlb.estore.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.mlb.estore.domain.User;

public class PrivilegeFilter implements Filter {
	// 存储权限url的集合
	List<String> adminLink = new ArrayList<String>();
	List<String> userLink = new ArrayList<String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			// 获取权限分配url的文件
			String adminPath = filterConfig.getServletContext().getRealPath(
					"/WEB-INF/classes/admin.txt");
			BufferedReader br1 = new BufferedReader(new FileReader(adminPath));
			String line1;
			while ((line1 = br1.readLine()) != null) {
				adminLink.add(line1);
			}
			br1.close();

			// 对于无法通过getRealPath获取资源路径的,用类加载器获取
			ClassLoader classLoader = this.getClass().getClassLoader();
			// 类加载器加载/WEB-INF/classes/目录下的文件url地址
			URL url = classLoader.getResource("user.txt");
			String userPath = url.getPath();
			BufferedReader br2 = new BufferedReader(new FileReader(userPath));
			String line2;
			while ((line2 = br2.readLine()) != null) {
				userLink.add(line2);
			}
			br2.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String requestURI = req.getRequestURI();
		String path = requestURI.substring(req.getContextPath().length());
		boolean inAdmin = adminLink.contains(path);
		boolean inUser = userLink.contains(path);
		if (!inAdmin && !inUser) {
			chain.doFilter(req, response);
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			if (user == null) {
				req.getRequestDispatcher("/login.jsp").forward(req, response);
				return;
			}
			if (inUser && "user".equals(user.getRole())) {
				chain.doFilter(req, response);
			} else if (inAdmin && "admin".equals(user.getRole())) {
				chain.doFilter(req, response);
			} else {
				throw new RuntimeException("权限不够,请联系超管!");
			}
		}

	}

	@Override
	public void destroy() {
	}

}
