package com.mlb.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mlb.estore.dao.CartDao;
import com.mlb.estore.domain.Cart;
import com.mlb.estore.utils.JDBCUtils;

public class CartDaoImpl implements CartDao {

	@Override
	public Cart findCart(String uid, String gid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from cart where uid = ? and gid = ?";
		try {

			return qr.query(sql, new BeanHandler<Cart>(Cart.class), uid, gid);
		} catch (SQLException e) {

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

			e.printStackTrace();
			throw new RuntimeException("删除失败");
		}
	}
}
