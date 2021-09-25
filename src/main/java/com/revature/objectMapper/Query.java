package com.revature.objectMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.revature.annotations.ForeignKey;
import com.revature.annotations.Nullable;
import com.revature.annotations.Unique;
import com.revature.util.ColumnField;
import com.revature.util.ConnectionUtil;
import com.revature.util.ForeignKeyField;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class Query {

	private static Logger log = Logger.getLogger(Query.class);

	// Temporary testing creation of table
	public boolean createTable(MetaModel<?> metamodel) {

        //HashMap<String, String> columns = new HashMap<>(); // still unused atm
        String tableName = metamodel.getEntity().tableName();
        IdField idField = metamodel.getPrimaryKey();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			Statement stmt = conn.createStatement();
			
			// drop table if exists
			stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName + " CASCADE");
			log.info("Table Dropped");
			
			// begin table query with ID
			String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(" + idField.getColumnName();
			
			// Check if serial. If not, make it varchar
			if (idField.testIsSerial()) {
				sql += " serial PRIMARY KEY, ";
			} else {
				String type = QueryHelper.getColumnType(idField.getType().toString());
				sql += type + " PRIMARY KEY, ";
			}
			
			
			// Add Foreign keys
			if (metamodel.getForeignKeys() != null) {

				for (ForeignKeyField fk : metamodel.setForeignKeys()) {
					String type = QueryHelper.getColumnType(fk.getType().toString());
					sql += fk.getColumnName() + " " + QueryHelper.getColumnType(fk.getType().toString());

					ForeignKey fkDetails = fk.getField().getAnnotation(ForeignKey.class);
					
					sql += " REFERENCES " + fkDetails.tableReference() + "(" + fkDetails.columnName() + ") ON DELETE CASCADE, ";
					
				}
			}
			
			// Add columns
			for (ColumnField cf : metamodel.getColumns()) {
				String type = QueryHelper.getColumnType(cf.getType().toString());
//				columns.put(cf.getColumnName(), type); // Maybe we'll need this later?? not sure
				sql += cf.getColumnName() + " " + type;
				

				
				Nullable nullable = cf.getField().getAnnotation(Nullable.class);
				Unique unique = cf.getField().getAnnotation(Unique.class);
				ForeignKey fk = cf.getField().getAnnotation(ForeignKey.class);

				if (nullable != null && nullable.isNullable() == false) {
					sql += " NOT NULL";
				}
				if (unique != null && unique.isUnique() != false) {
					sql += " UNIQUE";
				}
				
				sql += ", ";
			}
			
			// replace last , with )
			sql = sql.replaceAll(", $", ")");
			System.out.println(sql);
			
			//Finally execute 
			stmt.executeUpdate(sql);
			log.info("Table Created");
			
			return true;
		} catch (SQLException e) {
			log.info("Error: Could not create table");
			e.printStackTrace();
			return false;
		}
		
	}
	
}
