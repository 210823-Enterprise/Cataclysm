package com.revature.customStatement;

import java.util.ArrayList;
import java.util.List;

public class Delete {

	private String table;

	private List<String> wheres = new ArrayList<String>();

	public Delete(String table) {
		this.table = table;
	}

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder("DELETE FROM ").append(table);
		appendList(sql, wheres, " WHERE ", " AND ");
		return sql.toString();
	}

	public Delete where(String where) {
		wheres.add(where);
		return this;
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
