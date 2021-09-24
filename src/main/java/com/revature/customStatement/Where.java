package com.revature.customStatement;

public class Where {
	
	public String name; // Field name
	public Class valueDataType; // Datatype of first value to check
	public String value = ""; // Value to check
	public Class value2dataType; // Datatype of second value
	public String value2 = null; // Used in case we have AND or BETWEEN
	public final static String NULL = "NULL"; // Constant value of null
	public Conditionals conditional; // Optional conditional and between predicate. DEFaULT IS AND
	public Operators operator; // Optional list for type of filtering. DEFAULT IS =
	
	public Where() {
		conditional = Conditionals.AND;
		operator = Operators.EQUAL;
	}
	
	public String getOperatorString() {
		switch (this.operator) {
		
		case EQUAL:
			return "=";
		
		case NOT_EQUAL:
			return "!=";
			
		case GREATER_THAN:
			return ">";
		
		case GREATER_THAN_OR_EQUAL:
			return ">=";
			
		case LESS_THAN:
			return "<";
		
		case LESS_THAN_OR_EQUAL:
			return "<=";
			
		case IN:
			return "IN";
			
		case BETWEEN:
			return "BETWEEN";
			
		case LIKE:
			return "LIKE";
			
		case IS:
			return "IS";
			
		case IS_NOT:
			return "IS NOT";
			
		default:
			return null;
			
		}
	}

}
