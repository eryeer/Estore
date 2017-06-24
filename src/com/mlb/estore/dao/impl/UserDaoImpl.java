package com.mlb.estore.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.dao.UserDao;
import com.mlb.estore.domain.User;
import com.mlb.estore.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {
	final static Logger logger = LogManager.getLogger(UserDaoImpl.class);

	@Override
	public boolean findEmail(String email) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(1) from users where email = ?";
		try {

			Long result = qr.query(sql, new ScalarHandler<Long>(1), email);
			return result > 0;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public boolean register(User user) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into users values(?,?,?,?,?,?,?,?)";
		try {

			return qr.update(sql, user.getId(), user.getNickname(),
					user.getEmail(), user.getPassword(), user.getActivecode(),
					user.getStatus(), user.getRegistertime(), user.getRole()) > 0;
		} catch (SQLException e) {

			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public boolean activateUser(String id, String activecode) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update users set status = 1 where id = ? and activecode = ?";
		try {

			return qr.update(sql, id, activecode) > 0;
		} catch (SQLException e) {

			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public void deleteUser(String id) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "delete from users where id = ?";
		try {

			qr.update(sql, id);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public User findUserByIdAndActivecode(String id, String activecode) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from users where id = ? and activecode = ?";
		try {

			return qr.query(sql, new BeanHandler<User>(User.class), id,
					activecode);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	@Override
	public User findUser(User user) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from users where email = ? and password = ?";
		try {

			return qr.query(sql, new BeanHandler<User>(User.class),
					user.getEmail(), user.getPassword());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("找不到用户");
		}
	}
}
