package com.revature.customStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomColumn {
	public static void main(String[] args) {
		CustomColumn c = new CustomColumn("test_user")
				.datatype("INTEGER")
				.constraint("NOT NULL")
				.primaryKey(true)
				.deleteCascade(true);
		
		System.out.println(c.toString());
	}

	private List<Object> datatypes = new ArrayList<Object>();
	private List<Object> constraints = new ArrayList<Object>();
	private List<Object> references = new ArrayList<Object>();
	private List<Object> primaryKey = new ArrayList<Object>();
	private List<Object> deleteCascades = new ArrayList<Object>();

	private String columnName;

	public CustomColumn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CustomColumn deleteCascade(boolean is) {
		if (is) {
			deleteCascades.add("ON DELETE CASCADE");
		}
		return this;
	}
	
	public CustomColumn primaryKey(boolean is) {
		if (is) {
		primaryKey.add("PRIMARY KEY");
		}
		
		return this;
	}

	public CustomColumn columnName(String columnName) {
		this.columnName = columnName;
		return this;
	}

	public CustomColumn datatype(String datatype) {
		datatypes.add(datatype);
		return this;
	}

	public CustomColumn reference(String reference) {
		references.add(reference);
		return this;
	}

	public CustomColumn constraint(String constraint) {
		constraints.add(constraint);
		return this;
	}

	public CustomColumn(String columnName) {
		super();
		this.columnName = columnName;
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
	
	public String toString() {
        StringBuilder sql = new StringBuilder("").append(columnName).append("");
        appendList(sql, datatypes, " ", ", ");
        appendList(sql, primaryKey, " ", " ");
        appendList(sql, constraints, " ", " ");
        appendList(sql, references, " REFERENCES ", " ");
        appendList(sql, deleteCascades, " ", "");

        return sql.toString();
    }

}
