package com.revature.objectMapper;

import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.util.ColumnField;
import com.revature.util.ConnectionUtil;
import com.revature.util.MetaModel;

public class Query {

	private static Logger log = Logger.getLogger(Query.class);

	// Temporary testing creation of table
	public int createTable(MetaModel<?> metamodel) {

        HashMap<String, String> columns = new HashMap<>();
        String tableName = metamodel.getTableName().tableName();
        String id = metamodel.getPrimaryKey().getColumnName();
        
//		System.out.println(metamodel.getSimpleClassName());

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
			
			String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "("
					+ id + " serial PRIMARY KEY,";
			
			for (Entry<String, String> entry : columns.entrySet()) {
				sql += entry.getKey() + " " + entry.getValue() + ", ";
			}
			sql += ")";
			sql = sql.replaceAll(", \\)$", ")");
			System.out.println(sql);
			
			stmt.executeUpdate(sql);
			

			log.info("Table Created");
//			System.out.println("Created Table");
			return 1;
		} catch (SQLException e) {
			System.out.println("error");
			e.printStackTrace();
			return -1;
		}
		
	}
	
}
