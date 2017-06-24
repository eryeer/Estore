package com.mlb.estore.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.constants.ConstantInner;
import com.mlb.estore.dao.CartDao;
import com.mlb.estore.dao.GoodsDao;
import com.mlb.estore.dao.OrderDao;
import com.mlb.estore.domain.Order;
import com.mlb.estore.domain.OrderItem;
import com.mlb.estore.service.OrderService;
import com.mlb.estore.utils.FactoryUtils;

public class OrderServiceImpl implements OrderService {
	OrderDao orderDao = FactoryUtils.getInstance(OrderDao.class);
	final static Logger logger = LogManager.getLogger(OrderServiceImpl.class);

	@Override
	public List<Map<String, Object>> findCitiesByPid(String pid) {

		return orderDao.findCitiesByPid(pid);
	}

	@Override
	public void addOrder(Order order) {
		CartDao cartDao = FactoryUtils.getInstance(CartDao.class);

		orderDao.addOrder(order);
		orderDao.addOrderItems(order.getList());
		cartDao.deleteCarts(order.getUid());

	}

	@Override
	public List<Order> findOrderListByUid(String uid) {

		return orderDao.findOrderListByUid(uid);
	}

	@Override
	public void deleteOrder(String uid, String oid) {

		orderDao.deleteOrderitem(uid, oid);
		orderDao.deleteOrder(uid, oid);
	}

	@Override
	public Order findOrderDetailByOid(String oid) {
		Order order = orderDao.findOrderByOid(oid);
		List<OrderItem> list = orderDao.findOrderItemsByOid(oid);
		order.setList(list);
		return order;
	}

	@Override
	public void orderFinish(String oid) {
		orderDao.orderFinish(oid);
	}

	@Override
	public String findTotoalpriceByOid(String oid) {
		double totalprice = orderDao.findTotalpriceByOid(oid);
		DecimalFormat df = new DecimalFormat("#.00");
		String format = df.format(totalprice);
		return format;
	}

	@Override
	public void updateOrderExpire() {
		List<Order> list = orderDao.findAll();
		// 商品id和buynum的map
		Map<String, Integer> goodsMap = new HashMap<String, Integer>();
		// 24小时后订单过期
		int interval = 1000 * 60 * 60 * 24;
		if (list != null && list.size() > 0) {
			for (Order order : list) {
				long currentTime = new Date().getTime();
				long createTime = order.getCreatetime().getTime();
				if (order.getStatus() == ConstantInner.STATUS_ORDER.UNPAYED
						&& (currentTime - createTime > interval)) {
					// 更改订单状态
					orderDao.orderExpireById(order.getId());
					// 还原商品数量
					List<OrderItem> listOI = orderDao.findOrderItemsByOid(order
							.getId());
					for (OrderItem orderItem : listOI) {
						String gid = orderItem.getGid();
						int buynum = orderItem.getBuynum();
						if (goodsMap.containsKey(gid)) {
							goodsMap.put(gid, goodsMap.get(gid) + buynum);
						} else {
							goodsMap.put(gid, buynum);
						}
					}
				}
			}
		}
		if (goodsMap != null && goodsMap.size() > 0) {
			GoodsDao goodsDao = FactoryUtils.getInstance(GoodsDao.class);
			for (Entry<String, Integer> entry : goodsMap.entrySet()) {
				goodsDao.retrieveNumById(entry.getKey(), entry.getValue());
			}
		}
	}
}
