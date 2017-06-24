package com.mlb.estore.web.frontservlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final static Logger logger = LogManager.getLogger(BaseServlet.class);

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 获取调用servlet方法名
		String methodName = request.getParameter("method");
		if (methodName == null) {
			// 用户没有发送请求方法method ,系统自动跳转到主页
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		} else {
			try {
				Method method = this.getClass().getMethod(methodName,
						HttpServletRequest.class, HttpServletResponse.class);
				String result = (String) method.invoke(this, request, response);
				// 子类servlet业务方法如果有返回值 ,根据返回值进行对应的重定向或者转发操作
				// 如果以/sr开头 表示执行重定向操作 没有则表示转发
				if (result != null) {
					if (result.startsWith("/sr")) {
						result = result.substring(3);
						response.sendRedirect(request.getContextPath() + "/"
								+ result);
					} else {
						request.getRequestDispatcher(result).forward(request,
								response);
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

}
