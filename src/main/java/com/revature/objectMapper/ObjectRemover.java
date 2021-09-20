package com.revature.objectMapper;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.util.ConnectionUtil;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectRemover extends ObjectMapper {
	
	public boolean deleteById(Class clazz, int id) {
		
		MetaModel<?> model = MetaModel.of(clazz);
		Field[] fields = clazz.getDeclaredFields();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			IdField idF = model.getPrimaryKey();
			String sql = "DELETE FROM " + model.getEntity().tableName() + " WHERE " + idF.getColumnName() + " = ?";
			
			System.out.println(sql);
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			int rs = pStmt.executeUpdate();
			
			if (rs > 0) {
				System.out.println("Value " + id + " of " + idF.getColumnName() + " in " + model.getEntity().tableName() + " deleted succesfully");
				return true;
			} else {
				System.out.println("Something went wrong trying to delete");
				return false;
			}
			
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteTable(Class clazz) {
		
		MetaModel<?> model = MetaModel.of(clazz);
		Field[] fields = clazz.getDeclaredFields();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			IdField idF = model.getPrimaryKey();
			String sql = "DROP TABLE " + model.getEntity().tableName();
			
			System.out.println(sql);
			
			Statement stmt = conn.createStatement();
			boolean rs = stmt.execute(sql);
			
			if (rs) {
				System.out.println("Table " + model.getEntity().tableName() + " deleted succesfully");
				return true;
			} else {
				System.out.println("Something went wrong trying to delete");
				return false;
			}
			
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
