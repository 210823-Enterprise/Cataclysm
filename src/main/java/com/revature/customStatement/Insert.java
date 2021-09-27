package com.revature.customStatement;

import java.util.ArrayList;
import java.util.List;

public class Insert {

	private String table;

	private List<String> columns = new ArrayList<String>();

	private List<String> values = new ArrayList<String>();
	
	  public Insert(String table) {
	        this.table = table;
	    }

	    public Insert set(String column, String value) {
	        columns.add(column);
	        values.add(value);
	        return this;
	    }

	 
	    public String toString() {
	        StringBuilder sql = new StringBuilder("INSERT INTO ").append(table).append(" (");
	        appendList(sql, columns, "", ", ");
	        sql.append(") values (");
	        appendList(sql, values, "", ", ");
	        sql.append(")");
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
