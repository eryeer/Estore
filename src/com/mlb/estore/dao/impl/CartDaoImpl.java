package com.mlb.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.dao.CartDao;
import com.mlb.estore.domain.Cart;
import com.mlb.estore.utils.JDBCUtils;
import com.mlb.estore.utils.TransactionManager;

public class CartDaoImpl implements CartDao {
	final static Logger logger = LogManager.getLogger(CartDaoImpl.class);

	@Override
	public Cart findCart(String uid, String gid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from cart where uid = ? and gid = ?";
		try {

			return qr.query(sql, new BeanHandler<Cart>(Cart.class), uid, gid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("找不到该条购物车信息");
		}
	}

	@Override
	public void add(String uid, String gid, int buynum) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into cart values(?,?,?)";
		try {

			qr.update(sql, uid, gid, buynum);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("插入购物车信息失败");
		}
	}

	@Override
	public void update(String uid, String gid, int buynum) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update cart set buynum = ? where uid = ? and gid = ?";
		try {

			qr.update(sql, buynum, uid, gid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("更新购物车信息失败");
		}
	}

	@Override
	public List<Cart> findAllByUid(String uid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from cart where uid = ?";
		try {

			return qr.query(sql, new BeanListHandler<Cart>(Cart.class), uid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("购物车列表获取失败");
		}
	}

	@Override
	public void deleteCart(String uid, String gid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "delete from cart where uid = ? and gid = ?";
		try {

			qr.update(sql, uid, gid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("删除失败");
		}
	}

	@Override
	public Long getCartNumByUid(String uid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(1) from cart where uid = ?";
		try {

			return qr.query(sql, new ScalarHandler<Long>(1), uid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("获取购物车商品数量失败");
		}
	}

	@Override
	public void deleteCarts(String uid) {
		QueryRunner qr = new QueryRunner();
		String sql = "delete from cart where uid = ?";
		try {

			qr.update(TransactionManager.getCurrentConnection(), sql, uid);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("删除购物车出问题了");
		}
	}
}
