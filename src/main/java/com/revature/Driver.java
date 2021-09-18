package com.revature;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.revature.dummymodels.Test;
import com.revature.objectMapper.ObjectSaver;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class Driver {

	public static void main(String[] args) {
		
		ObjectSaver os = new ObjectSaver();

		Configuration cfg = new Configuration();	
		// In our configuration object we want to add annotated class, without ever having to instantiate them
		cfg.addAnnotatedClass(Test.class);
		
		// this is just to prove that we successfully transformed it to a metamodel, readable by our framework
		// let's iterate over all meta models that exist in the config object
		for (MetaModel<?> metamodel : cfg.getMetaModels()) {
			
			System.out.printf("Printing metamodel for class: %s\n", metamodel.getClassName()); // %s is a place holder
			
			IdField PK = metamodel.getPrimaryKey();
			List<ColumnField> columnFields = metamodel.setColumns();
			
			System.out.printf("ID column field named %s of type %s, which maps to the DB column %s\n", PK.getName(), PK.getType(), PK.getColumnName());

			for (ColumnField cf : columnFields) {
					
				System.out.printf("Found a column field named %s of type %s, which maps to the DB column %s\n", cf.getName(), cf.getType(), cf.getColumnName());
				
			}
		}
		
		for (MetaModel<?> metamodel : cfg.getMetaModels()) {
			
			cfg.addTable(metamodel);
		}
		
		Test t = new Test("jmliguid", "passowrd", 22, 180);
		os.insert(t);
		
		
//		MetaModel<?> model = MetaModel.of(t.getClass());
//		
//		
//		
//		List<ColumnField> fcolumns = model.setColumns();
//		
//		for (ColumnField fc : fcolumns) {
//			System.out.println(fc.getName());
//			fc.get();
//
//		}
//		
//		try {
//			for (ColumnField cf : fcolumns) {
//				PropertyDescriptor pd = new PropertyDescriptor(cf.getName(), t.getClass());
//				Method getter = pd.getReadMethod();
//				Object f = getter.invoke(t);
//				System.out.println(f);
//			}
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
//				| IntrospectionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	        
	    

	}
	private static void callSetter(Object obj, String fieldName, Object value) {
		PropertyDescriptor pd;
		
			try {
				pd = new PropertyDescriptor(fieldName, obj.getClass());
				pd.getWriteMethod().invoke(obj, value);
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	private static void callGetter(Object obj, String fieldName) {
		PropertyDescriptor pd;
	
			try {
				pd = new PropertyDescriptor(fieldName, obj.getClass());
				System.out.println("" + pd.getReadMethod().invoke(obj));
			} catch (IntrospectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
