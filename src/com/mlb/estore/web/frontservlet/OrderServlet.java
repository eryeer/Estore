package com.mlb.estore.web.frontservlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mlb.estore.domain.Cart;
import com.mlb.estore.domain.Order;
import com.mlb.estore.domain.OrderItem;
import com.mlb.estore.domain.User;
import com.mlb.estore.service.CartService;
import com.mlb.estore.service.OrderService;
import com.mlb.estore.utils.FactoryUtils;
import com.mlb.estore.utils.ProxyUtils;
import com.mlb.estore.utils.UUIDUtils;

import flexjson.JSONSerializer;

public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String orderSubmit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 验证用户登录
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "/login.jsp";
		}

		String uid = user.getId();
		CartService cartService = FactoryUtils.getInstance(CartService.class);
		List<Cart> cartList = cartService.findAllByUid(uid);
		List<OrderItem> oil = new ArrayList<OrderItem>();
		String oid = UUIDUtils.getUUID();
		double totalprice = 0;
		for (Cart cart : cartList) {
			OrderItem orderItem = new OrderItem(oid, cart.getGid(),
					cart.getBuynum());
			oil.add(orderItem);
			int buynum = cart.getBuynum();
			double estoreprice = cart.getGoods().getEstoreprice();
			totalprice += buynum * estoreprice;
		}
		String province = request.getParameter("province_name");
		String city = request.getParameter("city_name");
		String district = request.getParameter("district_name");
		String detailAddress = request.getParameter("detail_address");
		String postcode = request.getParameter("postcode");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		StringBuilder sb = new StringBuilder();
		sb.append(province).append(city).append(district).append(detailAddress)
				.append(postcode).append(name).append(tel);
		String address = sb.toString();
		// 1：待付款，2：已付款，3：已过期
		int status = 1;

		Timestamp createtime = new Timestamp(new Date().getTime());
		Order order = new Order(oid, uid, totalprice, address, status,
				createtime, oil);

		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);
		OrderService proxyOrderService = new ProxyUtils<OrderService>(
				orderService).getProxy();
		proxyOrderService.addOrder(order);
		request.setAttribute("order", order);
		return "/orderServlet?method=orderDetail&oid=" + order.getId();
	}

	public String orderList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 验证用户登录
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "/login.jsp";
		}
		String uid = user.getId();
		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);
		List<Order> list = orderService.findOrderListByUid(uid);
		request.setAttribute("olist", list);
		return "/orders.jsp";
	}

	public String orderDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 验证用户登录
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "/login.jsp";
		}
		String uid = user.getId();
		String oid = request.getParameter("oid");
		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);
		Order order = orderService.findOrderDetailByOid(oid);
		request.setAttribute("order", order);
		return "/orders_detail.jsp";
	}

	public String deleteOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 验证用户登录
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "/login.jsp";
		}
		String uid = user.getId();
		String oid = request.getParameter("oid");
		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);
		OrderService proxyOrderService = new ProxyUtils<OrderService>(
				orderService).getProxy();
		// 传入uid增强判断防止通过oid恶意删除
		proxyOrderService.deleteOrder(uid, oid);
		return "/srorderServlet?method=orderList";
	}

	public void findCitiesAJAX(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				response.getWriter().write("unlogin");
				return;
			}

			String pid = request.getParameter("pid");
			OrderService orderService = FactoryUtils
					.getInstance(OrderService.class);
			List<Map<String, Object>> list = orderService.findCitiesByPid(pid);
			JSONSerializer serializer = new JSONSerializer();
			String json = serializer.exclude("pid").serialize(list);
			response.setContentType("text/json");
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			response.getWriter().write("error");
		}

	}

}
