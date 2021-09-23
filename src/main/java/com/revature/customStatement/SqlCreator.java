package com.revature.customStatement;

import java.util.List;

import com.revature.util.ColumnField;

public class SqlCreator {
	
	private List<Where> where;
	private List<ColumnField> select;
	private Class from;
	
	public SqlCreator build() {
		return new SqlCreator();
	}

}
