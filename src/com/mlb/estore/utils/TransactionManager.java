package com.mlb.estore.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public static Connection getCurrentConnection() {
		Connection con = threadLocal.get();
		if (con == null) {
			try {
				con = JDBCUtils.getConnection();
			} catch (SQLException e) {

				e.printStackTrace();
				throw new RuntimeException("从连接池中获取连接失败");
			}
			threadLocal.set(con);
		}
		return con;
	}

	public static void begin() {
		Connection con = getCurrentConnection();
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException("开启事务失败");
		}
	}

	public static void rollback() {
		Connection con = getCurrentConnection();
		try {
			con.rollback();
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException("回滚事务失败");
		}
	}

	public static void end() {
		Connection con = getCurrentConnection();
		try {
			con.commit();
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException("提交事务失败");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
				throw new RuntimeException("连接关闭失败");
			} finally {

				threadLocal.remove();
			}
		}

	}
}
