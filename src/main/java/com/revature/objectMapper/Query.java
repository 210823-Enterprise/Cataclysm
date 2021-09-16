package com.revature.objectMapper;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import com.revature.util.ConnectionUtil;

public class Query {
	
	// Temporary testing creation of table
	public int createTable(Class<?> clazz) {

		System.out.println("`````````````````````````````````````````");
		System.out.println(clazz.getName());
		System.out.println("`````````````````````````````````````````");
		
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "CREATE TABLE IF NOT EXISTS test_table ("
					+ "id serial PRIMARY KEY,"
					+ "username VARCHAR(50),"
					+ "password VARCHAR(50) NOT NULL)";
			
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
			System.out.println("Created Table");
			return 1;
		} catch (SQLException e) {
			System.out.println("error");
			e.printStackTrace();
			return -1;
		}
		
	}
	
}
