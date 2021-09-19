package com.revature.objectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.revature.util.ColumnField;
import com.revature.util.ConnectionUtil;
import com.revature.util.MetaModel;
import com.revature.objectMapper.QueryHelper;

public class Query {

	private static Logger log = Logger.getLogger(Query.class);
	
	// CreateDB
	public boolean createDatabase(String DBName) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "CREATE DATABASE ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, DBName);
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				return true;
			}
		} catch (SQLException e) {
			System.out.println("error");
			e.printStackTrace();
			return false;
		}

		return false;
	}

	// Temporary testing creation of table
	public int createTable(MetaModel<?> metamodel) {

        HashMap<String, String> columns = new HashMap<>();
        String tableName = metamodel.getTableName().tableName();
        String id = metamodel.getPrimaryKey().getColumnName();

        // Get Column name and type
		List<ColumnField> columnFields = metamodel.getColumns();
		for (ColumnField cf : columnFields) {
			/**
			 * NEED ERROR HANDLING IF MULTIPLE COLUMNS HAVE THE SAME NAME
			 */
			String type = QueryHelper.getColumnType(cf.getType().toString());
			columns.put(cf.getColumnName(), type);
			System.out.println(cf.getColumnName() + " " + type);
		}
		
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			Statement stmt = conn.createStatement();
			
			// drop table if exists
			stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName);
			log.info("Table Dropped");
			
			// begin table query with ID
			String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "("
					+ id + " serial PRIMARY KEY,";
			
			// Add columns
			for (Entry<String, String> entry : columns.entrySet()) {
				sql += entry.getKey() + " " + entry.getValue() + ", ";
			}
			
			// replace last , with )
			sql = sql.replaceAll(", $", ")");
			System.out.println(sql);
			
			//Finally execute 
			stmt.executeUpdate(sql);
			log.info("Table Created");
			
			return 1;
		} catch (SQLException e) {
			System.out.println("error");
			e.printStackTrace();
			return -1;
		}
		
	}
	
}
