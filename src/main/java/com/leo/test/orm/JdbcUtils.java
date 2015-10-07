package com.leo.test.orm;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 操作数据
 * 
 * @author Leo
 * 
 */
public final class JdbcUtils {
	private static DataSource myDataSource = null;

	private JdbcUtils() {}

	static {
		/*
		 * 
		 * 读取配置文件，一次加载�?初始化时完成
		 */
		try {
			Properties prop = new Properties();
			InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
			prop.load(is);
			myDataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * 获取数据库连�?
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return myDataSource.getConnection();
	}

	/**
	 * 执行关闭操作
	 * 
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
}
