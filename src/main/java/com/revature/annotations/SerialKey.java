package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


// PrimaryKey and SerialKey are different. A SerialKey IS a primaryKey, but allows it to be automatically 
// incremented on each addition.

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerialKey {

	String columnName();
	
}