package com.mlb.estore.web.frontservlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.mlb.estore.domain.Cart;
import com.mlb.estore.domain.User;
import com.mlb.estore.service.CartService;
import com.mlb.estore.utils.FactoryUtils;
import com.mlb.estore.utils.ProxyUtils;

public class CartServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String addToCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 验证用户登录
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "/login.jsp";
		}

		String uid = user.getId();
		String gid = request.getParameter("gid");
		String buynum_str = request.getParameter("buynum");
		CartService cartService = FactoryUtils.getInstance(CartService.class);
		CartService proxyCartService = new ProxyUtils<CartService>(cartService)
				.getProxy();
		boolean result = proxyCartService.addToCart(uid, gid,
				Integer.parseInt(buynum_str));
		if (result) {

			return "/srbuyorcart.jsp";
		} else {
			response.setContentType("text/html");
			response.getWriter().write(
					"<script>alert('购买数量不能大于库存数量');history.go(-1);</script>");
			return null;
		}
	}

	public String cartList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 验证用户登录
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "/login.jsp";
		}
		CartService cartService = FactoryUtils.getInstance(CartService.class);
		List<Cart> list = cartService.findAllByUid(user.getId());
		request.setAttribute("list", list);
		String source = request.getParameter("source");
		// 判断是否转发至提交订单页
		if (StringUtils.equalsIgnoreCase(source, "cartJsp")) {
			return "/orders_submit.jsp";
		}
		return "/cart.jsp";
	}

	public void changeCartAJAX(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			response.setContentType("text/json");
			// 验证用户登录
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				response.getWriter().write("unlogin");
				return;
			}
			String gid = request.getParameter("gid");
			String buynum_str = request.getParameter("buynum");
			CartService cartService = FactoryUtils
					.getInstance(CartService.class);
			// 可能会有bug,这样不应该有新增操作,只应该有修改操作
			cartService.addToCart(user.getId(), gid,
					Integer.parseInt(buynum_str));

			response.getWriter().write("ok");
		} catch (Exception e) {

			e.printStackTrace();
			response.getWriter().write("error");
		}
	}

	public void deleteCartAJAX(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/json");
			// 验证用户登录
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				response.getWriter().write("unlogin");
				return;
			}
			String gid = request.getParameter("gid");
			String uid = user.getId();
			CartService cartService = FactoryUtils
					.getInstance(CartService.class);
			cartService.deleteCart(uid, gid);
			response.getWriter().write("delete complete");
		} catch (Exception e) {

			e.printStackTrace();
			response.getWriter().write("error");
		}
	}

	public void getCartNumAJAX(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/json");
			// 验证用户登录
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				response.getWriter().write("unlogin");
				return;
			}
			String uid = user.getId();
			CartService cartService = FactoryUtils
					.getInstance(CartService.class);
			Long num = cartService.getCartNumByUid(uid);
			response.getWriter().print(num);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			response.getWriter().write("error");
		}
	}
}
