package com.mlb.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mlb.estore.dao.GoodsDao;
import com.mlb.estore.domain.Goods;
import com.mlb.estore.utils.JDBCUtils;

public class GoodsDaoImpl implements GoodsDao {

	@Override
	public List<Goods> findAll() {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from goods";
		try {

			return qr.query(sql, new BeanListHandler<Goods>(Goods.class));
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public Long getTotalCount() {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(1) from goods";
		try {

			return qr.query(sql, new ScalarHandler<Long>(1));
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<Goods> findAll(int beginCount, int pageSize) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from goods limit ?,?";
		try {

			return qr.query(sql, new BeanListHandler<Goods>(Goods.class),
					beginCount, pageSize);
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public Goods findGoodsById(String id) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from goods where id = ?";
		try {

			return qr.query(sql, new BeanHandler<Goods>(Goods.class), id);
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
