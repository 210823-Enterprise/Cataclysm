package com.revature.objectMapper;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.annotations.Column;
import com.revature.annotations.Id;
import com.revature.util.ColumnField;
import com.revature.util.ConnectionUtil;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectReader {
	
	private static Logger log = Logger.getLogger(ObjectReader.class);

	public <T> Object selectRowWithId(Class clazz, int id) {

		@SuppressWarnings("unchecked")
		MetaModel<?> model = MetaModel.of(clazz);
		Field[] fields = clazz.getDeclaredFields();
		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM " + model.getEntity().tableName();
			
			IdField idF = model.getPrimaryKey();

			sql += " WHERE " + idF.getColumnName() + "= " + id + ";";

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
								log.warn("Could not set the value pulled from ResultSet to the variables.");
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
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == Long.class || cf.getType() == long.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getLong(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == Double.class || cf.getType() == double.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getDouble(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == Float.class || cf.getType() == float.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getFloat(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == BigDecimal.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getBigDecimal(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == boolean.class || cf.getType() == Boolean.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getBoolean(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == char.class || cf.getType() == Character.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getString(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == Date.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								try {
									cf.set(thisObj, rs.getTimestamp(fc.getColumnName()));
								} catch (IllegalArgumentException | IllegalAccessException e) {
									log.warn("Could not set the value pulled from ResultSet to the variables.");
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
								log.warn("IllegalAccess/IllegalArgument Exception was thrown.");
							}
						} else {

							try {
								byte[] data = Base64.getDecoder().decode(rs.getString(fc.getColumnName()));
								ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								ObjectOutputStream oos = null;
							} catch (IOException e) {
								log.warn("IOException was thrown.");
								e.printStackTrace();
							}

						}
					}
				}
				return thisObj;

			}
		} catch (SQLException e) {
			log.warn("SQL Exception was thrown.");
		} catch (InstantiationException e1) {
			log.warn("Instantiation Exception was thrown.");
		} catch (IllegalAccessException e1) {
			log.warn("Illegal Access Exception was thrown.");
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> selectAllFromTable(Class clazz) {
		List<T> objectList = new ArrayList<T>();
		PropertyDescriptor pd;
		MetaModel<?> model = MetaModel.of(clazz);
		Field[] fields = clazz.getDeclaredFields();

		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT * FROM " + model.getEntity().tableName();

			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Object thisObj=null;
				try {
					thisObj = clazz.getDeclaredConstructor().newInstance();
				} catch (InvocationTargetException | NoSuchMethodException | SecurityException e1) {
					log.warn("Could not set the value pulled from ResultSet to the variables.");
				}
				
				int fieldCount = 1;
				for (Field cf : fields) {
					Id id = cf.getAnnotation(Id.class);
					if (id != null) {
						cf.setAccessible(true);
						cf.set(thisObj, rs.getInt(id.columnName()));
						fieldCount++;
					}
				}
				for (Field cf : fields) {

					Column column = cf.getAnnotation(Column.class);
					if (column != null) {
						ColumnField fc = new ColumnField(cf);
						if (cf.getType() == String.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								cf.set(thisObj, rs.getString(fc.getColumnName()));
							}
						} else if (cf.getType() == int.class || cf.getType() == Integer.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								cf.set(thisObj, rs.getInt(fc.getColumnName()));
							} else {
								cf.setAccessible(true);
								cf.set(thisObj, rs.getInt(fc.getColumnName()));
							}
						} else if (cf.getType() == Long.class || cf.getType() == long.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								cf.set(thisObj, rs.getLong(fc.getColumnName()));
							}
						} else if (cf.getType() == Double.class || cf.getType() == double.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								cf.set(thisObj, rs.getDouble(fc.getColumnName()));
							}
						} else if (cf.getType() == Float.class || cf.getType() == float.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								cf.set(thisObj, rs.getFloat(fc.getColumnName()));
							}
						} else if (cf.getType() == BigDecimal.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								cf.set(thisObj, rs.getBigDecimal(fc.getColumnName()));
							}
						} else if (cf.getType() == boolean.class || cf.getType() == Boolean.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								cf.set(thisObj, rs.getBoolean(fc.getColumnName()));
							}
						} else if (cf.getType() == char.class || cf.getType() == Character.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								cf.set(thisObj, rs.getString(fc.getColumnName()));
							}
						} else if (cf.getType() == Date.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								try {
									cf.set(thisObj, rs.getTimestamp(fc.getColumnName()));
								} catch (IllegalArgumentException | IllegalAccessException e) {
									log.warn("Could not set the value pulled from ResultSet to the variables.");
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
								log.warn("IllegalArgument/IllegalAccess Excepiton thrown.");
							}
						} else {

							byte[] data = Base64.getDecoder().decode(rs.getString(fc.getColumnName()));
							ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							ObjectOutputStream oos = null;

						}

						fieldCount++;
					}
				}
				T newObj = (T) thisObj;
				objectList.add(newObj);

			}

			return objectList;
		} catch (SQLException e) {
			log.warn("SQL Exception was thrown.");
		} catch (IOException e) {
			log.warn("IOException was thrown.");
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.warn("Illegal Argument Exception was thrown.");
		} catch (InstantiationException e1) {
			log.warn("Instantiation Exception was thrown.");
		}

		return null;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public <T> Object customSelect(Class clazz, String sql) {

		Field[] fields = clazz.getDeclaredFields();
		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			int rsColumn = 1;
			while (rs.next()) {
				Object thisObj;
				thisObj = clazz.newInstance();
				for (Field cf : fields) {
					Id id = cf.getAnnotation(Id.class);
					if (!rs.equals(id.columnName())) {
						break;
					}
					if (id != null) {
						cf.setAccessible(true);
						cf.set(thisObj, rs.getInt(id.columnName()));
						log.info("Accessed id.");
						rsColumn++;
					}
				}
				for (Field cf : fields) {

					Column column = cf.getAnnotation(Column.class);
					
					if (column != null) {
						ColumnField fc = new ColumnField(cf);
						if (rsColumn > columnCount) {
							log.warn("Column count exceeded.");
							continue;
						}
	
						if (!rsmd.getColumnName(rsColumn).equals(fc.getColumnName())) {
							log.warn("Column was not found in result set.");
							continue;
						}
						if (cf.getType() == String.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getString(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
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
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == Long.class || cf.getType() == long.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getLong(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == Double.class || cf.getType() == double.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getDouble(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == Float.class || cf.getType() == float.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getFloat(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == BigDecimal.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getBigDecimal(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == boolean.class || cf.getType() == Boolean.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getBoolean(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == char.class || cf.getType() == Character.class) {
							try {
								if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
									cf.setAccessible(true);
									cf.set(thisObj, rs.getString(fc.getColumnName()));
								}
							} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else if (cf.getType() == Date.class) {
							if (cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
								cf.setAccessible(true);
								try {
									cf.set(thisObj, rs.getTimestamp(fc.getColumnName()));
								} catch (IllegalArgumentException | IllegalAccessException e) {
									log.warn("Could not set the value pulled from ResultSet to the variables.");
								} catch (SQLException e) {
									log.warn("Could not set the value pulled from ResultSet to the variables.");
								}
							}
						} else if (cf.getType() instanceof Class && ((Class<?>) cf.getType()).isEnum()
								&& cf.getName() != null && rs.getString(fc.getColumnName()) != null) {
							cf.setAccessible(true);
							try {
								cf.set(thisObj,
										Enum.valueOf((Class<Enum>) cf.getType(), rs.getString(fc.getColumnName())));
							} catch (IllegalArgumentException | IllegalAccessException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}
						} else {

							try {
								byte[] data = Base64.getDecoder().decode(rs.getString(fc.getColumnName()));
								ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								ObjectOutputStream oos = null;
							} catch (IOException e) {
								log.warn("Could not set the value pulled from ResultSet to the variables.");
							}

						}
						rsColumn++;
					}
					
				}
				return thisObj;

			}
		} catch (SQLException e) {
			log.warn("SQL Exception was thrown.");
		} catch (InstantiationException e1) {
			log.warn("Instantation Exception was thrown.");
		} catch (IllegalAccessException e1) {
			log.warn("Illegal Access Exception was thrown.");
		}

		return null;
	}
}
