package com.leo.test.orm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;


/**
 * JDBC相关方法
 * 
 * @author Leo
 * 
 */
public class MyJDBCOprate {

	/**
	 * 查找数据，统一返回List
	 * 
	 * @param sql eg.select * from user where id=?
	 * @param args 依照SQL里面？的顺序
	 * @param objectMapper
	 * @return
	 */
	public static List<Object> query(String sql, Object[] args,
			ObjectMapper objectMapper) {
		if (sql == null)
			throw new IllegalArgumentException("sql is null!");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> list = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (args != null)
				for (int i = 0; i < args.length; i++)
					ps.setObject(i + 1, args[i]);
			rs = ps.executeQuery();
			Object obj = null;
			list = new ArrayList<Object>();
			while (rs.next()) {
				obj = objectMapper.mapRow(rs);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	/**
	 * 更新、删除、插入操作
	 * 
	 * @param sql eg1. update user set status=? where id=?
	 * @param args 依照SQL里面？的顺序
	 */
	public static void updateOrsaveOrdelete(String sql, Object[] args) {
		if (sql == null)
			throw new IllegalArgumentException("sql is null!");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (args != null)
				for (int i = 0; i < args.length; i++)
					ps.setObject(i + 1, args[i]);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(null, ps, conn);
		}
	}

	/**
	 * 获取列表
	 * 
	 * @param <T>
	 * @param sql eg.select * from user where id=#id#
	 * @param domain
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(String sql, T domain) {
		if (null == domain||null==sql)
			throw new IllegalArgumentException("param is null");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Class<?> clazz = null;
		List<T> objects = null;
		List<String> args = null;
		try {
			clazz = domain.getClass();
			args = new ArrayList<String>();
			/*
			 * 处理SQL
			 */
			sql=getArgsFromSQL(sql,args);
			Map<String, Object> paraMap = getFieldsNameAndValue(domain);
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			/*
			 * 把参数放进去
			 */
			for (int index = 0; index < args.size(); index++) {
				Object parameter = paraMap.get(args.get(index));
				if (parameter == null)
					throw new IllegalArgumentException("argument don't match!");
				ps.setObject(index + 1, parameter);
			}
			rs = ps.executeQuery();
			String[] colNames = getColNames(rs);
			Method[] ms = clazz.getMethods();
			Map<String, Method> methodMap = new HashMap<String, Method>(ms.length);
			/*
			 * 放入 MAP，防止双重循环
			 */
			for (Method method : ms) {
				methodMap.put(method.getName(), method);
			}
			objects = new ArrayList<T>();
			/*
			 * 组装 List
			 */
			while (rs.next()) {
				T object = (T) clazz.newInstance();
				for (int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					colName = StringUtils.capitalize(colName);
					String methodName = "set" + colName;
					Method method = methodMap.get(methodName);
					if (method == null)
						throw new IllegalArgumentException(
								"Object's properties do not meet the rules");
					Class<?> parameterType = method.getParameterTypes()[0];
					method.invoke(object, ConvertUtils.convert(
							rs.getObject(colName), parameterType));
				}
				objects.add(object);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return objects;
	}

	/**
	 * 查找、删除、插入操作
	 * 
	 * @param <T>
	 * @param sql eg.update user set status=#status# where id=#id#
	 * @param domain
	 * @return
	 */
	public static <T> void updateOrsaveOrdelete(String sql, T domain) {
		if (null == domain||null==sql)
			throw new IllegalArgumentException("param is null");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> args = null;
		Class<?> clazz = null;
		try {

			args = new ArrayList<String>();
			/*
			 * 处理SQL
			 */
			sql=getArgsFromSQL(sql,args);
			Map<String, Object> paraMap = getFieldsNameAndValue(domain);
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			/*
			 * 把参数放进去
			 */
			for (int index = 0; index < args.size(); index++) {
				Object parameter = paraMap.get(args.get(index));
				if (parameter == null)
					throw new IllegalArgumentException("argument don't match!");
				ps.setObject(index + 1, parameter);
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			clazz = domain.getClass();
			Method[] ms = clazz.getMethods();
			Map<String, Method> methodMap = new HashMap<String, Method>(ms.length);
			/*
			 * 放入 MAP，防止双重循环
			 */
			for (Method method : ms) {
				methodMap.put(method.getName(), method);
			}
			if (rs.next()) {
				String methodName = "set" + "Id";
				Method method = methodMap.get(methodName);
				if (method == null)
					throw new IllegalArgumentException(
							"Object's properties do not meet the rules");
				Class<?> parameterType = method.getParameterTypes()[0];
				method.invoke(domain,
						ConvertUtils.convert(rs.getInt(1), parameterType));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	/**
	 * 返回对象List
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public static <T> List<T> getList(T t) {
		if (null == t)
			throw new IllegalArgumentException("对象为空");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		Class<?> clazz = null;
		List<T> objects = new ArrayList<T>();
		try {
			clazz = t.getClass();
			sql = new StringBuffer();
			String tableName = clazz.getName();
			tableName = tableName.substring(tableName.lastIndexOf(".") + 1,
					tableName.length());
			sql.append("select * from ").append(tableName.toLowerCase());
			Map<String, Object> map = getFieldsNameAndValue(t);
			if (!map.isEmpty())
				sql.append(" where ");
			int flag = 1;
			for (String key : map.keySet()) {
				flag++;
				if (flag > 1 && flag < (map.size() + 1))
					sql.append(key).append("=? and ");
				else if (flag == (map.size() + 1))
					sql.append(key).append("=? ");
				else
					sql.append(key).append("=? ");
			}
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql.toString());
			int j = 1;
			for (String key : map.keySet()) {
				ps.setObject(j, map.get(key));
				j++;
			}
			rs = ps.executeQuery();
			String[] colNames = getColNames(rs);
			Method[] ms = clazz.getMethods();
			while (rs.next()) {
				T object = (T) clazz.newInstance();
				for (int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					colName = StringUtils.capitalize(colName);
					String methodName = "set" + colName;
					for (Method m : ms) {
						if (methodName.equals(m.getName())
								&& rs.getObject(colName) != null) {
							Class<?> parameterType = m.getParameterTypes()[0];
							m.invoke(object, ConvertUtils.convert(
									rs.getObject(colName), parameterType));
							break;
						}
					}
				}
				objects.add(object);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return objects;
	}

	/**
	 * 根据对象属性更新表数据
	 * 
	 * @param <T>
	 * @param t
	 */
	@Deprecated
	public static <T> void update(T t) {
		if (null == t)
			throw new IllegalArgumentException("对象为空");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		Class<?> clazz = null;
		try {
			clazz = t.getClass();
			String tableName = clazz.getName();
			tableName = tableName.substring(tableName.lastIndexOf(".") + 1,
					tableName.length());
			conn = JdbcUtils.getConnection();
			sql = new StringBuffer();
			sql.append("update ").append(tableName.toLowerCase())
					.append(" set ");
			Map<String, Object> map = getFieldsNameAndValue(t);
			if (map.isEmpty())
				throw new IllegalArgumentException("没有参数");
			int flag = 1;
			for (String key : map.keySet()) {
				flag++;
				if (flag > 1 && flag < (map.size() + 1))
					sql.append(key).append("=? , ");
				else if (flag == (map.size() + 1))
					sql.append(key).append("=? ");
				else
					sql.append(key).append("=? ");
			}
			sql.append(" where id=?");
			ps = conn.prepareStatement(sql.toString());
			int j = 1;
			for (String key : map.keySet()) {
				ps.setObject(j, map.get(key));
				j++;
			}

			Field idField = t.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			Object id = idField.get(t);
			if (id == null)
				throw new RuntimeException("请先查出ID");
			ps.setObject(j, id);
			int count = ps.executeUpdate();
			if (0 >= count)
				throw new RuntimeException("操作数据库异常");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	/**
	 * 获取查询的列名
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static String[] getColNames(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		String[] colNames = new String[count];
		for (int i = 1; i <= count; i++) {
			colNames[i - 1] = rsmd.getColumnLabel(i);
		}
		return colNames;
	}

	/**
	 * 找到对象中值不为null的对象，并加name和value都保存到map中
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static <T> Map<String, Object> getFieldsNameAndValue(T t)
			throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object o = field.get(t);
			if (null != o)
				map.put(field.getName(), o);
		}
		return map;
	}
	
	private static String getArgsFromSQL(String sql,List<String> args){
		int begin=-1;
		while ((begin = sql.indexOf("#")) != -1) {
			String first = sql.substring(begin);
			int end = first.substring(1).indexOf("#")+1;
			args.add(first.substring(1, end));
			sql = sql.substring(0, begin) + " ? " + first.substring(end+1);
		}
		return sql;
	}
}