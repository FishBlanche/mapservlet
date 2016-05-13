package com.huanying.map.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 关闭数据库连接类
 * 
 * @author Administrator
 * 
 */
public class DBClose {

	private static ConnectionPool pool = ConnectionPool.getInstance();

	public static void close(Connection conn, PreparedStatement ps) {
		close(conn, ps, null);
	}

	public static void close(ResultSet rs) {
		close(null, null, rs);
	}

	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				pool.release(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
