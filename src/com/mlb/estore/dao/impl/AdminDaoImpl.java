package com.mlb.estore.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.dao.AdminDao;
import com.mlb.estore.domain.Goods;
import com.mlb.estore.utils.JDBCUtils;

public class AdminDaoImpl implements AdminDao {
	final static Logger logger = LogManager.getLogger(AdminDaoImpl.class);

	@Override
	public void addGoods(Goods goods) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into goods values(?,?,?,?,?,?,?,?)";
		try {

			qr.update(sql, goods.getId(), goods.getName(),
					goods.getMarketprice(), goods.getEstoreprice(),
					goods.getCategory(), goods.getNum(), goods.getImgurl(),
					goods.getDescription());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("添加商品失败");
		}
	}

}
