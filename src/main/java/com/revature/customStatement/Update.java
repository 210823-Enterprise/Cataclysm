package com.revature.customStatement;

import java.util.ArrayList;
import java.util.List;

public class Update {
	
	private String table;
	
	private List<String> sets = new ArrayList<String>();
	
    private List<String> wheres = new ArrayList<String>();
	
	public Update(String table) {
        this.table = table;
    }

    public Update set(String set) {
        sets.add(set);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder("update ").append(table);
        appendList(sql, sets, " SET ", ", ");
        appendList(sql, wheres, " WHERE ", " AND ");
        return sql.toString();
    }

    public Update where(String where) {
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
