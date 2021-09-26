package com.revature.customStatement;

import java.util.ArrayList;
import java.util.List;

public class Select {

	private List<String> columns = new ArrayList<String>();
	private List<Object> tables = new ArrayList<Object>();
	private List<Object> joins = new ArrayList<Object>();
	private List<Object> leftJoins = new ArrayList<Object>();
	private List<Object> wheres = new ArrayList<Object>();
	private List<Object> groupBys = new ArrayList<Object>();
	private List<Object> havings = new ArrayList<Object>();
	private List<Object> unions = new ArrayList<Object>();
	private List<Object> orderBys = new ArrayList<Object>();
	
	public static void main(String[] args) {
		Select select = new Select("user_table")
				.column("username")
				.column("password")
				.where("id = 1");
		System.out.println(select.toString());
	}

	public Select() {
		super();
	}

	public Select(String table) {
		tables.add(table);
	}

	public Select column(String name) {
		columns.add(name);
		return this;
	}

	public Select join(String join) {
		joins.add(join);
		return this;
	}

	public Select leftJoin(String join) {
		leftJoins.add(join);
		return this;
	}

	public Select where(String where) {
		wheres.add(where);
		return this;
	}

	public Select groupBy(String groupBy) {
		groupBys.add(groupBy);
		return this;
	}

	public Select having(String having) {
		havings.add(having);
		return this;
	}

	public Select unions(String union) {
		unions.add(union);
		return this;
	}

	public Select orderBys(String orderBy) {
		orderBys.add(orderBy);
		return this;
	}

	public String toString() {

		StringBuilder sql = new StringBuilder("SELECT ");

		if (columns.size() == 0) {
			sql.append("*");
		} else {
			appendList(sql, columns, "", ", ");
		}

		appendList(sql, tables, " FROM ", ", ");
		appendList(sql, joins, " join ", " join ");
		appendList(sql, leftJoins, " left join ", " left join ");
		appendList(sql, wheres, " WHERE ", " and ");
		appendList(sql, groupBys, " group by ", ", ");
		appendList(sql, havings, " having ", " and ");
		appendList(sql, unions, " union ", " union ");
		appendList(sql, orderBys, " order by ", ", ");

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
