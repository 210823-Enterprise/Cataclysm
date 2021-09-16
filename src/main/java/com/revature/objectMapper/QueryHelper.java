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
			return "integer";

		case "double":
			return "numeric";
			
		case "boolean":
			return "boolean";
			
		case "class java.lang.String":
			return "varchar";
		
		default:
			return "varchar";
		}
		
	}
}
