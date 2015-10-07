package com.leo.test.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper {
	public Object mapRow(ResultSet rs) throws SQLException;
}
