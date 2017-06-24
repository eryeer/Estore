package com.mlb.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.dao.OrderDao;
import com.mlb.estore.domain.Order;
import com.mlb.estore.domain.OrderItem;
import com.mlb.estore.utils.JDBCUtils;
import com.mlb.estore.utils.TransactionManager;

public class OrderDaoImpl implements OrderDao {
	final static Logger logger = LogManager.getLogger(OrderDaoImpl.class);

	@Override
	public List<Map<String, Object>> findCitiesByPid(String pid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from province_city_district where pid = ?";
		try {

			return qr.query(sql, new MapListHandler(), pid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("找不到cities");
		}
	}

	@Override
	public void addOrder(Order order) {

		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values (?,?,?,?,?,?)";
		try {

			qr.update(TransactionManager.getCurrentConnection(), sql,
					order.getId(), order.getUid(), order.getTotalprice(),
					order.getAddress(), order.getStatus(),
					order.getCreatetime());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("添加订单失败");
		}
	}

	@Override
	public void addOrderItems(List<OrderItem> list) {
		StringBuffer sb = new StringBuffer();
		for (OrderItem oi : list) {
			sb.append(",('").append(oi.getOid()).append("','")
					.append(oi.getGid()).append("',").append(oi.getBuynum())
					.append(")");
		}
		String string = sb.substring(1);
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitems values" + string;
		try {

			qr.update(TransactionManager.getCurrentConnection(), sql);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("添加订单明细失败");
		}
	}

	@Override
	public List<Order> findOrderListByUid(String uid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid = ?";
		try {

			return qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("订单列表查找失败");
		}
	}

	@Override
	public void deleteOrderitem(String uid, String oid) {
		QueryRunner qr = new QueryRunner();
		String sql = "delete from orderitems where oid = (select id from orders where uid = ? and id = ?)";
		try {

			qr.update(TransactionManager.getCurrentConnection(), sql, uid, oid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("删除订单明细失败");
		}
	}

	@Override
	public void deleteOrder(String uid, String oid) {
		QueryRunner qr = new QueryRunner();
		String sql = "delete from orders where uid = ? and id = ?";
		try {

			qr.update(TransactionManager.getCurrentConnection(), sql, uid, oid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("删除订单失败");
		}
	}

	@Override
	public Order findOrderByOid(String oid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where id = ?";
		try {

			return qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("订单获取失败");
		}

	}

	@Override
	public List<OrderItem> findOrderItemsByOid(String oid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orderitems, goods where orderitems.oid = ? and orderitems.gid = goods.id";
		try {

			return qr.query(sql,
					new BeanListHandler<OrderItem>(OrderItem.class), oid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("订单详情获取失败");
		}
	}

	@Override
	public void orderFinish(String oid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update orders set status = 2 where id = ?";
		try {

			qr.update(sql, oid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public double findTotalpriceByOid(String oid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select totalprice from orders where id = ?";
		try {

			return qr.query(sql, new ScalarHandler<Double>(1), oid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<Order> findAll() {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders";
		try {

			return qr.query(sql, new BeanListHandler<Order>(Order.class));
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("查询账单列表错误");
		}
	}

	@Override
	public void orderExpireById(String id) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update orders set status = 3 where id = ?";
		try {

			qr.update(sql, id);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("账单使过期错误");
		}
	}
}
