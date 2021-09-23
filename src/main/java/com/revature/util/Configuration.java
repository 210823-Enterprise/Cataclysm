package com.revature.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.revature.annotations.Column;
import com.revature.annotations.Id;
import com.revature.dummymodels.Test;
import com.revature.objectMapper.Query;

public class Configuration {
	

	private List<MetaModel<Class<?>>> metaModelList;
	private List<String> databases;
	
	// this essentially does what the Hibernate.cfg.xml mapping property does!
	public Configuration addAnnotatedClass(Class annotatedClass) throws NoSuchFieldException, SecurityException {
		
		if (metaModelList == null) {
			
			metaModelList = new LinkedList<>();
			
		}

		
		metaModelList.add(MetaModel.of(annotatedClass)); // we will make this of() method
		
		// Create the of() method inside MetaModel to transform a class
		// into an appropriate data model to be transposed into a relational db object.
		
		return this;
	}
	
	public List<String> addDatabase(String db) {
		databases.add(db);
		return databases;
	}
	
	public void init() {
		for (MetaModel<?> metamodel : getMetaModels()) {
			
			System.out.printf("Printing metamodel for class: %s\n", metamodel.getClassName()); // %s is a place holder
			
			IdField PK = metamodel.getPrimaryKey();
			List<ColumnField> columnFields = metamodel.setColumns();
			
			System.out.printf("ID column field named %s of type %s, which maps to the DB column %s\n", PK.getName(), PK.getType(), PK.getColumnName());

			for (ColumnField cf : columnFields) {
					
				System.out.printf("Found a column field named %s of type %s, which maps to the DB column %s\n", cf.getName(), cf.getType(), cf.getColumnName());
				
			}
		}


		for (MetaModel<?> metamodel : getMetaModels()) {
			addTable(metamodel);
		}
	}

	public void addTable(MetaModel<?> metamodel) {
		Query q = new Query();
		q.createTable(metamodel);
		
	}
	
	public List<MetaModel<Class<?>>> getMetaModels() {
		
		return (metaModelList == null) ? Collections.emptyList() : metaModelList;
		
	}
	
	
	// Don't need this atm
//	for (String s: databases) {
//		createDatabase(s);
//	}
	
	//Don't need ?
//	public void createDatabase(String db) {
//		Query q = new Query();
//		q.createDatabase(db);
//	}
	

}




