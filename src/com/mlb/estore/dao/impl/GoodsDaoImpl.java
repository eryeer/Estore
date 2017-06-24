package com.mlb.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.dao.GoodsDao;
import com.mlb.estore.domain.Goods;
import com.mlb.estore.utils.JDBCUtils;

public class GoodsDaoImpl implements GoodsDao {
	final static Logger logger = LogManager.getLogger(GoodsDaoImpl.class);

	@Override
	public List<Goods> findAll() {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from goods";
		try {

			return qr.query(sql, new BeanListHandler<Goods>(Goods.class));
		} catch (SQLException e) {
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<Goods> getRanking() {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT goods.*,SUM(orderitems.buynum) AS salesnum FROM goods,orderitems,orders WHERE goods.id=orderitems.gid AND orderitems.oid = orders.id AND orders.status = 2 GROUP BY goods.id ORDER BY salesnum DESC";

		try {

			return qr.query(sql, new BeanListHandler<Goods>(Goods.class));
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int findNumById(String gid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select num from goods where id = ?";
		try {

			return qr.query(sql, new ScalarHandler<Integer>(), gid);
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public void updateNumById(String id, int i) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update goods set num = ? where id = ?";
		try {

			qr.update(sql, i, id);
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public void retrieveNumById(String id, int retrieveNum) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update goods set num = num + ? where id = ?";
		try {

			qr.update(sql, retrieveNum, id);
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
