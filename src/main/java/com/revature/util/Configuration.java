package com.revature.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Configuration {
	
	
	// all jdbc properties needed to establish a connection with the database
	
	private String dbUrl;
	private String username;
	private String password;
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
	
	
	public List<MetaModel<Class<?>>> getMetaModels() {
		
		return (metaModelList == null) ? Collections.emptyList() : metaModelList;
		
	}
	
	

}




