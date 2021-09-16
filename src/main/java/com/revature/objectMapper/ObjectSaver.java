package com.revature.objectMapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.util.List;

import com.revature.dummymodels.Test;
import com.revature.util.ColumnField;
import com.revature.util.MetaModel;

/**
 * Class which handles saving objects to the database.
 */
public class ObjectSaver extends ObjectMapper {
	public static void main(String[] args) {
		Object obj = new Test();
		MetaModel<?> model = MetaModel.of(obj.getClass()); // Brings out test class
		List<ColumnField> columns = model.getColumns();
		for (ColumnField col : columns) {
			System.out.println(col.getColumnName() + ", ");
		}
	}
	public static final ObjectSaver objSaver = new ObjectSaver();

	public boolean saveObjectToDb(Object obj, Connection conn) {
		
		String columnString = "";
		MetaModel<?> model = MetaModel.of(obj.getClass()); // Brings out test class
		List<ColumnField> columns = model.getColumns();
		for (ColumnField col : columns) {
			System.out.println(col);
		}
		
		    
		
		String primaryKey = model.getPrimaryKey().getName(); // change this to IdField
		String sql = "INSERT INTO " + model.getSimpleClassName() + " ( " + columnString. + "= ?"; // create some type
																									// of method that
//		INSERT INTO joshua_l.users (username, pwd, u_role)
//		VALUES ('jmliguid', 'password', 'Customer' ),																						// returns the table
																									// name in
																									// MetaModel;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		// we want to grab meta data from this statement
		ParameterMetaData pd = pstmt.getParameterMetaData();

		// instead of Method, maybe pass in a hashmap containing info about the object
		// that you
		setStatement(pstmt, pd, obj, 1);

		// ObjectCache class...

		// then call acustom setStatement method

		pstmt.executeUpdate();

	}

}
