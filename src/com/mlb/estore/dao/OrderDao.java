package com.mlb.estore.dao;

import java.util.List;
import java.util.Map;

import com.mlb.estore.domain.Order;
import com.mlb.estore.domain.OrderItem;

public interface OrderDao {

	List<Map<String, Object>> findCitiesByPid(String pid);

	void addOrder(Order order);

	void addOrderItems(List<OrderItem> list);

	List<Order> findOrderListByUid(String uid);

	void deleteOrder(String uid, String oid);

	Order findOrderByOid(String oid);

	List<OrderItem> findOrderItemsByOid(String oid);

	void deleteOrderitem(String uid, String oid);

	void orderFinish(String oid);

	double findTotalpriceByOid(String oid);

	List<Order> findAll();

	void orderExpireById(String id);

}
