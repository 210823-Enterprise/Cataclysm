package com.revature.objectMapper;

public class QueryHelper {

	public static String getColumnType(String s) {
		
		/*
		 * TO ADD
		 * TEXT
		 * ARRAY?
		 */
		
		switch (s) {
		case "int":
			return "INTEGER";

		case "double":
			return "NUMERIC";
			
		case "boolean":
			return "BOOLEAN";
			
		case "long":
			return "BIGINT";
			
		case "class java.lang.String":
			return "VARCHAR";
		
		default:
			return "TEXT";
		}
		
	}
}
