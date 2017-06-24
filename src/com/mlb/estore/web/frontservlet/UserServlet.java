package com.mlb.estore.web.frontservlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.mlb.estore.constants.ConstantInner;
import com.mlb.estore.domain.User;
import com.mlb.estore.service.UserService;
import com.mlb.estore.utils.ApacheMailUtils;
import com.mlb.estore.utils.FactoryUtils;
import com.mlb.estore.utils.MD5Utils;
import com.mlb.estore.utils.UUIDUtils;

public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public void validEmail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		UserService userService = FactoryUtils.getInstance(UserService.class);
		boolean existEmail = userService.findEmail(email);
		response.getWriter().write(existEmail ? "true" : "false");
	}

	public void validCaptcha(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String captcha_input = request.getParameter("captcha");
		String captcha_server = (String) request.getSession().getAttribute(
				"captcha");
		if (captcha_server != null) {
			if (captcha_server.equalsIgnoreCase(captcha_input)) {
				response.getWriter().write("correct");
			} else {
				response.getWriter().write("wrong");
			}
		} else {
			response.getWriter().write("invalid");
		}
	}

	public String register(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String captcha_input = request.getParameter("captcha");
		String captcha_server = (String) request.getSession().getAttribute(
				"captcha");
		request.getSession().removeAttribute("captcha");
		if (captcha_server == null || captcha_server.equals("")) {
			request.setAttribute("wrong_message", "验证码过期");
			return "/register.jsp";
		}
		if (!StringUtils.equalsIgnoreCase(captcha_input, captcha_server)) {
			request.setAttribute("wrong_message", "验证码错误");
			return "/register.jsp";
		}

		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		// 校验空值和密码不一致
		String pwd2 = request.getParameter("confirm_password");
		if (StringUtils.isBlank(user.getNickname())) {
			request.setAttribute("wrong_message", "昵称不能为空");
			return "/register.jsp";
		} else if (StringUtils.isBlank(user.getEmail())) {
			request.setAttribute("wrong_message", "邮箱不能为空");
			return "/register.jsp";
		} else if (StringUtils.isEmpty(user.getPassword())) {
			request.setAttribute("wrong_message", "密码不能为空");
			return "/register.jsp";
		} else if (!StringUtils.equals(user.getPassword(), pwd2)) {
			request.setAttribute("wrong_message", "密码与确认密码不一致");
			return "/register.jsp";
		}
		// 封装user存入数据库
		user.setId(UUIDUtils.getUUID());
		user.setRegistertime(new Timestamp(new Date().getTime()));
		user.setStatus(ConstantInner.STATUS_USER.INACTIVATED);
		user.setActivecode(UUIDUtils.getUUID());
		user.setRole("user");
		user.setPassword(MD5Utils.md5(user.getPassword()));
		UserService userService = FactoryUtils.getInstance(UserService.class);
		boolean result = userService.register(user);
		if (result) {
			// 发送验证邮件
			String msg = "<a href='http://127.0.0.1:8080"
					+ request.getContextPath()
					+ "/userServlet?method=activation&id=" + user.getId()
					+ "&activecode=" + user.getActivecode() + "'> 点击链接激活 </a>";
			ApacheMailUtils.sendMail(user.getEmail(), "Estore注册激活", msg);
			return "/sractivation_message.jsp";

		} else {
			response.setContentType("text/html");
			response.getWriter().write("出错了,请返回注册页面重新注册");
			response.setHeader("refresh", "2;url=" + request.getContextPath()
					+ "register.jsp");
		}
		return null;
	}

	public String activation(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String id = request.getParameter("id");
		String activecode = request.getParameter("activecode");
		// 验证激活用户
		UserService userService = FactoryUtils.getInstance(UserService.class);
		User existUser = userService.findUserByIdAndActivecode(id, activecode);
		// 检验用户是否存在
		if (existUser == null) {
			request.setAttribute("wrong_message", "激活失败重新注册");
			return "/register.jsp";
		}
		// 验证时间是否超过一天
		Timestamp registertime = existUser.getRegistertime();
		long time = registertime.getTime();
		long now = new Date().getTime();
		if ((now - time) >= 1000 * 3600 * 24
				&& existUser.getStatus() == ConstantInner.STATUS_USER.INACTIVATED) {
			request.setAttribute("wrong_message", "激活码超时,重新注册");
			userService.deleteUser(id);
			return "/register.jsp";
		}
		// 已经注册直接跳转登录页面
		if (existUser.getStatus() == ConstantInner.STATUS_USER.ACTIVATED) {
			return "/srlogin.jsp";
		}
		// 激活验证码
		boolean result = userService.activateUser(id, activecode);
		if (result) {

			response.getWriter().print("账号验证成功");
		} else {
			request.setAttribute("wrong_message", "验证失败,重新注册");
			userService.deleteUser(id);
			return "/register.jsp";
		}
		return null;
	}

	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String remember = request.getParameter("remember");
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		// 非空验证
		if (StringUtils.isBlank(user.getEmail())) {
			request.setAttribute("wrong_message", "用户名不能为空");
			return "/login.jsp";
		} else if (StringUtils.isEmpty(user.getPassword())) {
			request.setAttribute("wrong_message", "密码不能为空");
			return "/login.jsp";
		}
		// md5加密
		user.setPassword(MD5Utils.md5(user.getPassword()));

		// 数据库验证
		UserService userService = FactoryUtils.getInstance(UserService.class);
		User loginedUser = userService.findUser(user);
		if (loginedUser == null) {
			request.setAttribute("wrong_message", "用户名或密码错误");
			return "/login.jsp";
		} else if (loginedUser.getStatus() == 0) {
			request.setAttribute("wrong_message", "账号未激活");
			return "/login.jsp";
		}
		request.getSession().setAttribute("user", loginedUser);

		// 记住用户名
		Cookie cookie = null;
		if (remember != null) {
			// 用户名转码
			String email_cookie = URLEncoder.encode(user.getEmail(), "UTF-8");
			cookie = new Cookie("remember_email", email_cookie);
			cookie.setMaxAge(3600 * 24 * 7);
		} else {
			cookie = new Cookie("remember_email", "");
			cookie.setMaxAge(0);
		}
		cookie.setPath("/");
		response.addCookie(cookie);

		return "/srindex.jsp";

	}

	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getSession().removeAttribute("user");
		return "/srlogin.jsp";
	}

}
