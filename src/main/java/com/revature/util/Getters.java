package com.revature.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.revature.annotations.Column;
import com.revature.annotations.Getter;

public class Getters {
	private Method method;
	
	public Getters(Method method) {
		
		if (method.getAnnotation(Getter.class) == null) {
			// If the field object that we pass through DOESN't have the column annotation, then it returns null
			throw new IllegalStateException("Cannot create ColumnField Object! Provided field " + getName() + " is not annotated with @Column");
		}
		
		this.method = method;
		
	}
	
	public String getName() {
		return method.getName();
	}
	
	public Class<?> getReturnType() {
		return method.getReturnType();
	}
	
}
