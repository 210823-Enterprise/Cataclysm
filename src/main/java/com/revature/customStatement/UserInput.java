package com.revature.customStatement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.revature.annotations.Column;
import com.revature.annotations.Id;
import com.revature.dummymodels.Test;
import com.revature.util.ColumnField;
import com.revature.util.ConnectionUtil;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class UserInput {

	public static void main(String[] args) {
		String sql = "SELECT test_username, test_password, test_weight FROM test_table WHERE test_username = 'jmligz';";

		List<ColumnField> select = selectFields(sql);

		for (ColumnField cf : select) {
			System.out.println(cf.getField());
		}
	}

	public Object select(String sql, Class clazz) {
		MetaModel<?> model = MetaModel.of(clazz);
		List<Field> fields = getFields(sql);
		
		if (fields.isEmpty()) {
			Field[] fs = clazz.getDeclaredFields();
			for (Field f : fs) {
				fields.add(f);
			}
		}

		System.out.println(sql);
		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Object thisObj;
				thisObj = clazz.newInstance();
				for (Field cf : fields) {
					Id id2 = cf.getAnnotation(Id.class);
					if (id2 != null) {
						cf.setAccessible(true);
						cf.set(thisObj, rs.getInt(id2.columnName()));
					}
				}
				for (Field cf : fields) {

					Column column = cf.getAnnotation(Column.class);
					if (column != null) {
						ColumnField fc = new ColumnField(cf);
						if (cf.getType() == String.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getString(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (cf.getType() == int.class || cf.getType() == Integer.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getInt(fc.getColumnName()));
								} else {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getInt(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (cf.getType() == Long.class || cf.getType() == long.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getLong(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (cf.getType() == Double.class || cf.getType() == double.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getDouble(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (cf.getType() == Float.class || cf.getType() == float.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getFloat(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (cf.getType() == BigDecimal.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getBigDecimal(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (cf.getType() == boolean.class || cf.getType() == Boolean.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getBoolean(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (cf.getType() == char.class || cf.getType() == Character.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getString(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (cf.getType() == Date.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								try {
									cf.set(thisObj, rs.getTimestamp(fc.getColumnName()));
								} catch (IllegalArgumentException | IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SQLException e) {

								}
							}
						} else if (cf.getType() instanceof Class && ((Class<?>) cf.getType()).isEnum()
								&& cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
							cf.setAccessible(true);
							try {
								cf.set(thisObj,
										Enum.valueOf((Class<Enum>) cf.getType(), rs.getString(fc.getColumnName())));
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {

							try {
								byte[] data = Base64.getDecoder().decode(rs.getString(fc.getColumnName()));
								ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								ObjectOutputStream oos = null;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}
				return thisObj;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}

	public static List<ColumnField> selectFields(String sql) {
		List<ColumnField> select = new ArrayList<>();

		sql = StringUtils.substringBetween(sql, "SELECT ", " FROM");
		String[] words = sql.replaceAll(" ", "").split(",");

		MetaModel<?> model = MetaModel.of(Test.class);
		List<ColumnField> cf = model.setColumns();

		for (String word : words) {
			word.replaceAll(" ", "");
			for (ColumnField column : cf) {
				if (word.equals(column.getColumnName())) {
					select.add(column);
				}
			}
		}

		return select;
	}

	public static List<ColumnField> getWhere(String sql) {
		List<ColumnField> where = new ArrayList<>();
		sql = sql.toLowerCase();
		sql = StringUtils.substringBetween(sql, "where ", " =");
		String[] words = sql.replaceAll(" ", "").split(",");

		MetaModel<?> model = MetaModel.of(Test.class);
		List<ColumnField> cf = model.setColumns();

		for (String word : words) {
			word.replaceAll(" ", "");
			for (ColumnField column : cf) {
				if (word.equals(column.getColumnName())) {
					where.add(column);
				}
			}
		}

		return where;

	}

	public static String getValue(String sql) {
		String value = "'";
		value += StringUtils.substringBetween(sql, "= ", ";") + "'";
		return value;
	}

	public static Class getClass(String sql) {
		List<ColumnField> cfield = getWhere(sql);
		Class clazz = null;
		for (ColumnField c : cfield) {
			clazz = c.getClass();
		}

		return clazz;
	}

	public List<Field> getFields(String sql) {
		List<Field> fields = new ArrayList<>();
		List<ColumnField> select = selectFields(sql);

		for (ColumnField cf : select) {
			fields.add(cf.getField());
		}
		
		return fields;

	}

}
