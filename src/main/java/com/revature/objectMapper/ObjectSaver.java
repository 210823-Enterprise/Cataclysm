package com.revature.objectMapper;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Base64;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dummymodels.Test;
import com.revature.util.ColumnField;
import com.revature.util.ConnectionUtil;
import com.revature.util.MetaModel;

/**
 * Class which handles saving objects to the database.
 */
public class ObjectSaver extends ObjectMapper {

	public static final ObjectSaver objSaver = new ObjectSaver();
	
	private static Logger log = Logger.getLogger(ObjectUpdater.class);

	public int insert(Object obj) {

		MetaModel<?> model = MetaModel.of(obj.getClass());

		int newId = -1;

		int numberOfFields = 0;
		List<ColumnField> cfields = model.setColumns();
		for (ColumnField cf : cfields) {
			numberOfFields++;
		}

		String sql = "INSERT INTO " + model.getEntity().tableName() + "(";

		int fieldCounter = 0;
		for (ColumnField cf : cfields) {
			fieldCounter++;
			sql += " " + cf.getColumnName();
			if (numberOfFields > fieldCounter) {
				sql += ",";
			}
		}

		sql += " ) VALUES (";

		int fieldCounter2 = 0;
		for (ColumnField cf : cfields) {
			fieldCounter2++;
			sql += " ?";
			if (numberOfFields > fieldCounter2) {
				sql += ",";
			}
		}

		sql += ");";
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;

		try (Connection conn1 = ConnectionUtil.getConnection()) {

			preparedStmt = conn1.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int fieldCounter3 = 1;
			for (ColumnField cf : cfields) {
				PropertyDescriptor pd = new PropertyDescriptor(cf.getName(), obj.getClass());
				Method getter = pd.getReadMethod();
				Object value = getter.invoke(obj);

				if (cf.getType() == String.class) {
					if (value != null) {
						preparedStmt.setString(fieldCounter3, value.toString());
					} else {
						preparedStmt.setNull(fieldCounter3, Types.VARCHAR);
					}
				} else if (cf.getType() == int.class || cf.getType() == Integer.class) {
					if (value != null) {
						preparedStmt.setInt(fieldCounter3, (int) value);
					} else {
						preparedStmt.setNull(fieldCounter3, Types.INTEGER);
					}
				} else if (cf.getType() == Long.class || cf.getType() == long.class) {
					if (value != null) {
						preparedStmt.setLong(fieldCounter3, (long) value);
					} else {
						preparedStmt.setNull(fieldCounter3, Types.BIGINT);
					}
				} else if (cf.getType() == Double.class || cf.getType() == double.class) {
					if (value != null) {
						preparedStmt.setDouble(fieldCounter3, (double) value);
					} else {
						preparedStmt.setNull(fieldCounter3, Types.BIGINT);
					}
				} else if (cf.getType() == Float.class || cf.getType() == float.class) {
					if (value != null) {
						preparedStmt.setFloat(fieldCounter3, (float) value); 
					} else {
						preparedStmt.setNull(fieldCounter3, Types.BIGINT);
					}
				} else if (cf.getType() == BigDecimal.class) {
					if (value != null) {
						preparedStmt.setBigDecimal(fieldCounter3, (BigDecimal) value); 
					} else {
						preparedStmt.setNull(fieldCounter3, Types.NUMERIC);
					}
				} else if (cf.getType() == boolean.class || cf.getType() == Boolean.class) {
					if (value != null) {
						preparedStmt.setBoolean(fieldCounter3, (boolean) value);
					} else {
						preparedStmt.setNull(fieldCounter3, Types.TINYINT);
					}
				} else if (cf.getType() == char.class || cf.getType() == Character.class) {
					if (value != null && value.toString() != null && value.toString().substring(0, 1) != null) {
						
						Character convert = value.toString().charAt(0);
						
						if (Character.isLetterOrDigit(value.toString().charAt(0))) {
							preparedStmt.setString(fieldCounter3, convert.toString());
						} else {
							preparedStmt.setNull(fieldCounter3, Types.CHAR);
						}
					} else {
						preparedStmt.setNull(fieldCounter3, Types.CHAR);
					}
				} else if (cf.getType() == Date.class) {
					Date insertDate = (Date) value;
					if (insertDate == null) {
						preparedStmt.setTimestamp(fieldCounter3, null);
					} else {
						preparedStmt.setTimestamp(fieldCounter3, new Timestamp(insertDate.getTime()));
					}	
				} else {
					
					if (value != null) {
						
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutputStream oos = null;
						
						try {
							oos = new ObjectOutputStream(baos);
							oos.writeObject(value);
							oos.close();
							
							preparedStmt.setString(fieldCounter3, Base64.getEncoder().encodeToString(baos.toByteArray()));
						
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						preparedStmt.setNull(fieldCounter3, Types.BLOB);
					}
				}
				
				fieldCounter3++;
			}
			
			preparedStmt.execute();
			
			rs = preparedStmt.getGeneratedKeys();
			rs.next();
			newId = rs.getInt(1);
			
			PropertyDescriptor pd = new PropertyDescriptor(model.getPrimaryKey().getName(), obj.getClass());
			pd.getWriteMethod().invoke(obj, newId);
			
			ObjectCache.getInstance().insertToCache(obj);
			

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return newId;
	}
	
	public int customInsert(String sql) {

		int newId = -1;

		try (Connection conn = ConnectionUtil.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs;
			stmt.execute();
			
			rs = stmt.getGeneratedKeys();
			rs.next();
			newId = rs.getInt(1);
		} catch (SQLException e) {
			log.warn("We were unable to insert the account.");
			e.printStackTrace();
			return -1;
		}
		
	
		return newId;
	}

}
