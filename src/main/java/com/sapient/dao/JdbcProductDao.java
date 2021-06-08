package com.sapient.dao;

import java.sql.*;
import java.util.ResourceBundle;

import javax.management.RuntimeErrorException;

import lombok.*;

@Getter
@Setter
public class JdbcProductDao implements ProductDao {

	private String driverClassName;
	private String url;
	private String user;
	private String pwd;

	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driverClassName);
		return DriverManager.getConnection(url, user, pwd);
	}

	@Override
	public long count() {
		try (Connection conn = createConnection();
				PreparedStatement stmnt = conn.prepareStatement("select count(*) from products");
				ResultSet rs = stmnt.executeQuery();) {
			rs.next();
			return rs.getLong(1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
//		return 100;
	}

}
