package com.mlb.estore.web.frontservlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mlb.estore.domain.Cart;
import com.mlb.estore.domain.User;
import com.mlb.estore.service.CartService;
import com.mlb.estore.utils.FactoryUtils;

public class CartServlet extends BaseServlet {
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
		cartService.addToCart(uid, gid, Integer.parseInt(buynum_str));
		return "/srbuyorcart.jsp";
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
		return "/cart.jsp";
	}

	public void changeCartAJAX(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
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
}
