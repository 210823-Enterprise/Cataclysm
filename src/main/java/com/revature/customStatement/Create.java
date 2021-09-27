package com.revature.customStatement;

import java.util.ArrayList;
import java.util.List;

public class Create {
	
	public static void main(String[] args) {
		CustomColumn c = new CustomColumn("acc_owner")
				.datatype("INTEGER")
				.constraint("NOT NULL")
				.primaryKey(true)
				.reference("account(user_id)");
		
		CustomColumn c2 = new CustomColumn("account")
				.datatype("INTEGER")
				.constraint("NOT NULL")
				.reference("account(id)")
				.primaryKey(true)
				.deleteCascade(true);
		
		Create table = new Create("account_joint_table")
				.column(c)
				.column(c2);
		
		System.out.println(table.toString());
	}
	
	private String table;
	
	private List<String> columns = new ArrayList<String>();
	
	public Create(String table) {
		super();
		this.table = table;
	}
	
	public Create() {
		super();
	}
	
	public Create column(CustomColumn customColumn) {
		String sql = customColumn.toString();
		columns.add(sql);
		return this;
	}
	
	public String toString() {
		StringBuilder sql = new StringBuilder("DROP TABLE IF EXISTS " ).append(table).append(" CASCADE;\n");
		sql.append("CREATE TABLE ").append(table).append(" (\n");
        appendList(sql, columns, "", ",\n");
        sql.append("\n);");
        return sql.toString();
    }
    
    protected void appendList(StringBuilder sql, List<?> list, String beginning, String seperator) {

		boolean first = true;

		for (Object s : list) {
			if (first) {
				sql.append(beginning);
			} else {
				sql.append(seperator);
			}
			sql.append(s);
			first = false;
		}
	}
	
	
	

}
