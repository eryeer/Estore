package com.mlb.estore.service;

import java.util.List;
import java.util.Map;

import com.mlb.estore.domain.Order;

public interface OrderService {

	List<Map<String, Object>> findCitiesByPid(String pid);

	void addOrder(Order order);

	List<Order> findOrderListByUid(String uid);

	void deleteOrder(String uid, String oid);

	Order findOrderDetailByOid(String oid);

	void orderFinish(String oid);

	String findTotoalpriceByOid(String orderid);

	void updateOrderExpire();

}
