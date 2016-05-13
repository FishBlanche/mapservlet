package com.huanying.map.jdbc;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.huanying.map.util.JarTool;


public class ConnectionPool {
	private Vector<Connection> pool;
	private String url;
	private String username;
	private String password;
	private String driverClassName;

	/**
	 * 连接池的大小，也就是连接池中有多少个数据库连接�??
	 */
	private int poolSize = 1;
	private static ConnectionPool instance = null;

	/**
	 * 私有的构造方法，禁止外部创建本类的对象，要想获得本类的对象，<br>
	 * 通过 <code>getIstance</code>方法�? 使用了设计模式中的单子模式�??
	 */
	private ConnectionPool() {
		init();
	}

	/**
	 * 连接池初始化方法，读取属性文件的内容 建立连接池中的初始连�?
	 */
	private void init() {
		pool = new Vector<Connection>(poolSize);
		readConfig();
		addConnection();
	}

	/**
	 * 返回连接到连接池�?
	 */
	public synchronized void release(Connection conn) {
		pool.add(conn);
	}

	/**
	 * 关闭连接池中的所有数据库连接
	 */
	public synchronized void closePool() {
		for (int i = 0; i < pool.size(); i++) {
			try {
				((Connection) pool.get(i)).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pool.remove(i);
		}
	}

	/**
	 * 返回当前连接池的�?个对�?
	 */
	public static ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	/**
	 * 返回连接池中的一个数据库连接
	 */
	public synchronized Connection getConnection() {
		if (pool.size() > 0) {
			Connection conn = pool.get(0);
			pool.remove(conn);
			return conn;
		} else {
			return null;
		}
	}

	/**
	 * 在连接池中创建初始设置的的数据库连接
	 */
	private void addConnection() {
		Connection conn = null;
		for (int i = 0; i < poolSize; i++) {
			try {
				Class.forName(driverClassName);
				conn = java.sql.DriverManager.getConnection(url, username,
						password);
				pool.add(conn);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "数据库驱动加载错�?!\n", "程序将关�?",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "数据库连接错误！\n", "程序将关�?",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
	}

	/**
	 * 读取设置连接池的属�?�文�?
	 */
	private void readConfig() {
		try {
			String configPath = JarTool.getJarPath() + "/config.properties";
			FileInputStream is = new FileInputStream(configPath);
			Properties props = new Properties();
			props.load(is);
			this.driverClassName = props.getProperty("driverClassName").trim();
			this.username = props.getProperty("username").trim();
			this.password = props.getProperty("password").trim();
			this.poolSize = Integer.parseInt(props.getProperty("poolSize")
					.trim());
			this.url = "jdbc:mysql://" + props.getProperty("dbIp").trim() + ":"
					+ props.getProperty("dbPort").trim() + "/"
					+ props.getProperty("dbname").trim();
			System.out.println(url);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "配置文件读取错误,请您重新配置\n"
					+ "服务器数据库配置文件:\n " + JarTool.getJarPath()
					+ "/config.properties" + "\n读取错误！\n", "程序将关�?",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	public int size() {
		return pool.size();
	}
}
