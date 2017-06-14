package com.mlb.estore.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static DataSource dataSource = null;
	static {
		dataSource = new ComboPooledDataSource();

	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static void releaseSource(Connection con, PreparedStatement pst,
			ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public static void releaseSource(Connection con, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
}
