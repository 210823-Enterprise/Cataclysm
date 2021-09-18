package com.revature.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.revature.objectMapper.Query;

public class Configuration {
	

	private List<MetaModel<Class<?>>> metaModelList;
	
	// this essentially does what the Hibernate.cfg.xml mapping property does!
	public Configuration addAnnotatedClass(Class annotatedClass) {
		
		if (metaModelList == null) {
			
			metaModelList = new LinkedList<>();
			
		}
		
		metaModelList.add(MetaModel.of(annotatedClass)); // we will amke this of() method
		
		// Create the of() method inside MetaModel to trasform a class
		// into an appropraite data model to be transposed into a relational db object.
		
		return this;
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
	
	

}




