package com.huanying.map.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.huanying.map.jdbc.ConnectionPool;
import com.huanying.map.jdbc.DBClose;


/*
 * 提供针对SQL数据源的通用类CitySee_DATA
 */
public abstract class BaseDao {

	/*
	 * 执行单向操作(insert,update,delete)的方�? sql:执行打单向语�? params：sql语句的一组参�?
	 * returns:sql语句执行后影响的行数 eg:
	 * executeUpdate("insert into test values(?,?,?)",new Object[]{1,'a','�?'})
	 */
	public int executeUpdate(String sql, Object[] params) {
		Connection conn = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sql);
			// 传�?�参�?
			if (params != null) {
				if (params.length > 0) {
					for (int i = 0; i < params.length; i++) {
						ps.setObject(i + 1, params[i]);
					}
				}
			}
			// 执行
			int val = ps.executeUpdate();
			return val;
		} catch (SQLException e) {
			System.out.println("SQL语句有问�?!");
			e.printStackTrace();
			return -1;
		} finally {
			DBClose.close(conn, ps);
		}
	}

	/*
	 * 带有事务的一组操�? sqls：一组DDL语句 params:为sqls中的DDL语句提供参数
	 */
	public void executeTrans(String[] sqls, Object[][] params) {
		Connection conn = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);
			// 事务中包含sqls.length个操�?
			for (int i = 0; i < sqls.length; i++) {
				// 事务中的�?个操�?
				ps = conn.prepareStatement(sqls[i]);
				if (params[i] != null) {
					if (params[i].length > 0) {
						// 传参�?
						for (int j = 0; j < params[i].length; j++) {
							ps.setObject(j + 1, params[i][j]);
						}
					}
				}
				ps.executeUpdate();
			}

			// 提交事务
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DBClose.close(conn, ps);
		}

	}

	/**
	 * 执行双向操作(select)的方�? <br>
	 * eg: executeQuery("select * from emp where deptno=?",new Object[]{10})
	 * 
	 * @param sql
	 *            执行的双向语�?
	 * @param params
	 *            sql语句的一组参�?
	 * @return 结果�?
	 */
	public ResultSet executeQuery(String sql, Object... params) {
		Connection conn = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			// 传�?�参�?
			if (params != null) {
				if (params.length > 0) {
					for (int i = 0; i < params.length; i++) {
						ps.setObject(i + 1, params[i]);
					}
				}
			}
			// 执行
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			System.out.println("SQL语句有问�?!");
			e.printStackTrace();
			return null;
		} finally {
			DBClose.close(conn, null);
		}
	}

	public Object executeSingle(String sql, Object... params) {
		Connection conn = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			// 传�?�参�?
			if (params != null) {
				if (params.length > 0) {
					for (int i = 0; i < params.length; i++) {
						ps.setObject(i + 1, params[i]);
					}
				}
			}
			// 执行
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getObject(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("SQL语句有问�?!");
			e.printStackTrace();
			return null;
		} finally {
			DBClose.close(conn, ps, rs);
		}
	}
}
